package arcanegolem.yms.account.data.repos

import arcanegolem.yms.account.data.remote.api.Accounts
import arcanegolem.yms.account.data.remote.models.Account
import arcanegolem.yms.account.data.remote.models.AccountResponse
import arcanegolem.yms.account.data.remote.models.AccountUpdate
import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.account.domain.models.TransactionsByDayOfMonthModel
import arcanegolem.yms.account.domain.models.TransactionsForDay
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.dao.QueueDao
import arcanegolem.yms.core.data.database.dao.TransactionsDao
import arcanegolem.yms.core.data.database.entities.QueueEntity
import arcanegolem.yms.core.data.database.entities.QueueOperationType
import arcanegolem.yms.core.data.database.entities.TransactionEntity
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.datastore.models.AccountInfoModel
import arcanegolem.yms.core.data.remote.api.Transactions
import arcanegolem.yms.core.data.remote.models.TransactionResponse
import arcanegolem.yms.core.data.utils.currentDateTimeAsInstantString
import arcanegolem.yms.core.data.utils.formatCash
import arcanegolem.yms.core.data.utils.formatCashBackwards
import arcanegolem.yms.core.data.utils.formatCurrency
import arcanegolem.yms.core.data.utils.formatCurrencyBackwards
import arcanegolem.yms.core.data.utils.getDayNumberFromMillis
import arcanegolem.yms.core.data.utils.monthStartMillis
import arcanegolem.yms.core.data.utils.parseMillis
import arcanegolem.yms.core.data.utils.toDateStringYYYYMMDD
import arcanegolem.yms.core.data.utils.todayMillis
import arcanegolem.yms.core.domain.models.TransactionIntermediateModel
import arcanegolem.yms.core.domain.models.TransactionModel
import arcanegolem.yms.core.utils.NetworkMonitor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.time.ExperimentalTime

/**
 * Реальная имплементация репозитория для работы с счетами
 *
 * @param httpClient Ktor http-клиент
 * @param dataStoreManager хелпер для датастора с данными активного счета
 */
class AccountRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val dataStoreManager: DataStoreManager,
  private val queueDao: QueueDao,
  private val transactionsDao: TransactionsDao,
  private val categoryDao: CategoryDao
) : AccountRepository {
  override suspend fun loadAccount(accountId: Int): AccountModel {
    val response = httpClient.get(Accounts.Id(id = accountId)).body<AccountResponse>()
    
    return AccountModel(
      name = response.name,
      balance = response.balance.formatCash(response.currency),
      currency = response.currency.formatCurrency()
    )
  }
  
  override suspend fun refreshActiveAccount() {
    val response = httpClient.get(Accounts()).body<List<Account>>()
    val first = response.first()
    
    dataStoreManager.updateActiveAccount(
      AccountInfoModel(
        id = first.id,
        currency = first.currency,
        name = first.name,
        balance = first.balance.formatCash(first.currency),
        updatedAt = first.updatedAt
      )
    )
  }
  
  override suspend fun getAccount(): Flow<AccountModel?> {
    if (NetworkMonitor.networkAvailable.value) {
      refreshActiveAccount()
    }
    
    return dataStoreManager.getActiveAccountFlow().map { infoModel ->
      infoModel?.let {
        AccountModel(
          name = it.name,
          balance = it.balance,
          currency = it.currency.formatCurrency()
        )
      }
    }
  }
  
  @OptIn(ExperimentalTime::class)
  override suspend fun updateAccount(model: AccountModel) {
    val accountId: Int =
      dataStoreManager.getActiveAccount()?.id ?: throw RuntimeException("No active account!")
    
    val payload = AccountUpdate(
      name = model.name,
      balance = model.balance.formatCashBackwards(),
      currency = model.currency.formatCurrencyBackwards()
    )
    
    if (NetworkMonitor.networkAvailable.value) {
      val response = httpClient.put(Accounts.Id(id = accountId)) {
        setBody(payload)
        header("Content-Type", "application/json")
      }.body<Account>()
      
      dataStoreManager.updateActiveAccount(
        AccountInfoModel(
          id = response.id,
          balance = response.balance.formatCash(response.currency),
          currency = response.currency,
          name = response.name,
          updatedAt = response.updatedAt
        )
      )
    } else {
      queueDao.upsertToQueue(
        QueueEntity(
          identifier = QueueOperationType.ACCOUNT_UPDATE(),
          type = QueueOperationType.ACCOUNT_UPDATE(),
          payload = Json.encodeToString(payload)
        )
      )
      
      val existingModel =
        dataStoreManager.getActiveAccount() ?: throw RuntimeException("No active account!")
      dataStoreManager.updateActiveAccount(
        existingModel.copy(
          balance = model.balance.formatCashBackwards(),
          currency = model.currency.formatCurrencyBackwards(),
          name = model.name,
          updatedAt = currentDateTimeAsInstantString()
        )
      )
    }
  }
  
  override suspend fun getAccountTransactionsForThisMonth(): Flow<TransactionsByDayOfMonthModel> {
    val periodStartMillis = monthStartMillis()
    val periodEndMillis = todayMillis()
    
    val periodStartFormatted = periodStartMillis.toDateStringYYYYMMDD()
    val periodEndFormatted = periodEndMillis.toDateStringYYYYMMDD()
    
    if (NetworkMonitor.networkAvailable.value) {
      refreshActiveAccount()
      
      val accountInfo = dataStoreManager.getActiveAccount()
      val accountId = accountInfo?.id ?: throw RuntimeException("No active account!")
      
      val response = httpClient
        .get(
          Transactions.Account.ById.Period(
            Transactions.Account.ById(accountId = accountId),
            startDate = periodStartFormatted,
            endDate = periodEndFormatted
          )
        ).body<List<TransactionResponse>>()
      
      response.forEach { remote ->
        transactionsDao.upsertTransaction(
          TransactionEntity(
            transactionId = remote.id,
            categoryId = remote.category.id,
            amount = remote.amount,
            transactionDateMillis = remote.transactionDate.parseMillis(),
            createdAt = remote.createdAt,
            updatedAt = remote.updatedAt,
            comment = remote.comment ?: "",
            isIncome = remote.category.isIncome
          )
        )
      }
    }
    
    return transactionsDao.getAllTransactionsForPeriod(
      periodStart = periodStartMillis,
      periodEnd = periodEndMillis
    )
      .combine(
        transactionsDao.getAllArbitraryTransactionsForPeriod(
          periodStart = periodStartMillis,
          periodEnd = periodEndMillis
        )
      ) { realTransactions, arbitrary ->
        realTransactions.map {
          TransactionIntermediateModel(
            transactionId = it.transactionId,
            categoryId = it.categoryId,
            amount = it.amount,
            transactionDateMillis = it.transactionDateMillis,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            comment = it.comment,
            isIncome = it.isIncome,
            isArbitrary = false
          )
        } + arbitrary.map {
          TransactionIntermediateModel(
            transactionId = it.arbitraryId,
            categoryId = it.categoryId,
            amount = it.amount,
            transactionDateMillis = it.transactionDateMillis,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            comment = it.comment,
            isIncome = it.isIncome,
            isArbitrary = true
          )
        }
      }
      .combine(dataStoreManager.getActiveAccountFlow()) { transactionEntities, accountInfo ->
        TransactionsByDayOfMonthModel(
          year = periodEndFormatted.split("-")[0],
          month = periodEndFormatted.split("-")[1],
          transactionsByDay = buildList {
            val sortedEntities = transactionEntities
              .sortedBy { it.transactionDateMillis }
            
            var prevDayNumber =
              sortedEntities.firstOrNull()?.transactionDateMillis?.getDayNumberFromMillis()
                ?: return@buildList
            var dailyTransactions = TransactionsForDay(
              dayOfMonth = prevDayNumber.toString(),
              dayTotal = "0.0",
              transactions = emptyList()
            )
            
            sortedEntities.forEach { entity ->
              val relCategory = categoryDao.getCategoryById(entity.categoryId)
              val dayNumber = entity.transactionDateMillis.getDayNumberFromMillis()
              val amountFormatted = (if (entity.isIncome) "" else "-") +
                  entity.amount.formatCash(accountInfo?.currency ?: "")
              if (dayNumber == prevDayNumber) {
                dailyTransactions = dailyTransactions.copy(
                  dayTotal = (if (entity.isIncome)
                    dailyTransactions.dayTotal.toFloat() + entity.amount.toFloat()
                  else dailyTransactions.dayTotal.toFloat() - entity.amount.toFloat()).toString(),
                  transactions = dailyTransactions.transactions + TransactionModel(
                    id = entity.transactionId,
                    emoji = relCategory?.emoji ?: "",
                    label = relCategory?.name ?: "",
                    comment = entity.comment,
                    amountFormatted = amountFormatted,
                    dateTimeMillis = entity.transactionDateMillis,
                    isArbitrary = entity.isArbitrary
                  )
                )
              }
              if (dayNumber != prevDayNumber) {
                dailyTransactions = dailyTransactions.copy(
                  dayTotal = dailyTransactions.dayTotal.formatCash(accountInfo?.currency ?: "")
                )
                add(dailyTransactions)
                dailyTransactions = TransactionsForDay(
                  dayOfMonth = dayNumber.toString(),
                  dayTotal = if (entity.isIncome) entity.amount else "-" + entity.amount,
                  transactions = listOf(
                    TransactionModel(
                      id = entity.transactionId,
                      emoji = relCategory?.emoji ?: "",
                      label = relCategory?.name ?: "",
                      comment = entity.comment,
                      amountFormatted = amountFormatted,
                      dateTimeMillis = entity.transactionDateMillis,
                      isArbitrary = entity.isArbitrary
                    )
                  )
                )
                prevDayNumber = dayNumber
              }
            }
            
            add(
              dailyTransactions.copy(
                dayTotal = dailyTransactions.dayTotal.formatCash(accountInfo?.currency ?: "")
              )
            )
          }
        )
      }
  }
}
