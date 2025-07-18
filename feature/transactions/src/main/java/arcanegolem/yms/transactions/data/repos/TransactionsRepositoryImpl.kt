package arcanegolem.yms.transactions.data.repos

import android.util.Log
import arcanegolem.yms.account.domain.usecases.LoadAccountRemoteUseCase
import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.dao.QueueDao
import arcanegolem.yms.core.data.database.dao.TransactionsDao
import arcanegolem.yms.core.data.database.entities.ArbitraryTransactionEntity
import arcanegolem.yms.core.data.database.entities.QueueEntity
import arcanegolem.yms.core.data.database.entities.QueueOperationType
import arcanegolem.yms.core.data.database.entities.TransactionEntity
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.utils.compositeDate
import arcanegolem.yms.core.data.utils.currentDateTimeAsInstantString
import arcanegolem.yms.core.data.utils.formatCash
import arcanegolem.yms.core.data.utils.getDateFromInstantString
import arcanegolem.yms.core.data.utils.getTimeFromInstantString
import arcanegolem.yms.core.data.utils.monthStartMillis
import arcanegolem.yms.core.data.utils.parseMillis
import arcanegolem.yms.core.data.utils.toDateStringYYYYMMDD
import arcanegolem.yms.core.data.utils.todayMillis
import arcanegolem.yms.core.utils.NetworkMonitor
import arcanegolem.yms.transactions.data.models.TransactionIntermediateModel
import arcanegolem.yms.transactions.data.remote.api.Transactions
import arcanegolem.yms.transactions.data.remote.models.TransactionCreate
import arcanegolem.yms.transactions.data.remote.models.TransactionCreateResponse
import arcanegolem.yms.transactions.data.remote.models.TransactionDelete
import arcanegolem.yms.transactions.data.remote.models.TransactionResponse
import arcanegolem.yms.transactions.data.remote.models.TransactionUpdate
import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.transactions.domain.models.TransactionModel
import arcanegolem.yms.transactions.domain.models.TransactionsTotaledModel
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Реальная имплементация репозитория транзакций
 *
 * @param httpClient Ktor http-клиент
 * @param dataStoreManager хелпер для датастора с данными активного счета
 */
class TransactionsRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val dataStoreManager: DataStoreManager,
  private val loadAccountRemoteUseCase: LoadAccountRemoteUseCase,
  private val transactionsDao: TransactionsDao,
  private val categoryDao: CategoryDao,
  private val queueDao: QueueDao
) : TransactionsRepository {
  @OptIn(ExperimentalTime::class)
  override suspend fun loadTransactionsForPeriod(
    periodStartMillis: Long?,
    periodEndMillis: Long?,
    isIncome: Boolean
  ): Flow<TransactionsTotaledModel> {
    if (NetworkMonitor.networkAvailable.value) {
      val periodStartFormatted = periodStartMillis?.toDateStringYYYYMMDD()
      val periodEndFormatted = periodEndMillis?.toDateStringYYYYMMDD()

      loadAccountRemoteUseCase.execute()
      val accountId = dataStoreManager.getActiveAccount()?.id

      val response = accountId?.let {
        httpClient
          .get(
            Transactions.Account.ById.Period(
              Transactions.Account.ById(accountId = accountId),
              startDate = periodStartFormatted,
              endDate = periodEndFormatted
            )
          ).body<List<TransactionResponse>>()
      } ?: emptyList()

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

    return transactionsDao.getTransactionsForPeriod(
      periodStart = periodStartMillis ?: monthStartMillis(),
      periodEnd = periodEndMillis ?: todayMillis(),
      isIncome = isIncome
    )
      .combine(
        transactionsDao.getArbitraryTransactionsForPeriod(
          periodStart = periodStartMillis ?: monthStartMillis(),
          periodEnd = periodEndMillis ?: todayMillis(),
          isIncome = isIncome
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
        TransactionsTotaledModel(
          total = transactionEntities
            .map { it.amount.toFloat() }
            .sum()
            .toString()
            .formatCash(accountInfo?.currency ?: ""),
          transactions = transactionEntities
            .sortedBy { Instant.parse(it.createdAt) }
            .reversed()
            .map {
              val relCategory = categoryDao.getCategoryById(it.categoryId)
              TransactionModel(
                id = it.transactionId,
                emoji = relCategory?.emoji ?: "",
                label = relCategory?.name ?: "",
                comment = it.comment,
                amountFormatted = it.amount.formatCash(accountInfo?.currency ?: ""),
                dateTimeMillis = it.transactionDateMillis,
                isArbitrary = it.isArbitrary
              )
          }
        )
      }
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun updateTransaction(id: Int, model: TransactionInfoModel) {
    val accountId = dataStoreManager.getActiveAccount()?.id
      ?: throw RuntimeException("No active account!")

    val payload = TransactionUpdate(
      transactionId = id,
      accountId = accountId,
      categoryId = model.categoryId,
      amount = model.amount,
      transactionDate = compositeDate(model.date, model.time),
      comment = model.comment
    )

    if (NetworkMonitor.networkAvailable.value){
      val response = httpClient
        .put(Transactions.Id(id = id)) {
          setBody(payload)
          header("Content-Type", "application/json")
        }
      val httpStatusCode = response.status

      if (httpStatusCode.value == 200) {
        val body = response.body<TransactionResponse>()

        transactionsDao.upsertTransaction(
          TransactionEntity (
            transactionId = body.id,
            categoryId = body.category.id,
            amount = body.amount,
            transactionDateMillis = body.transactionDate.parseMillis(),
            updatedAt = body.updatedAt,
            createdAt = body.createdAt,
            comment = body.comment ?: "",
            isIncome = categoryDao.getCategoryById(body.category.id)?.isIncome ?: false
          )
        )
      }
    } else {
      if (model.isArbitrary) {
        queueDao.upsertToQueue(
          QueueEntity(
            identifier = "${id}_${QueueOperationType.TRANSACTION_UPDATE}",
            type = QueueOperationType.TRANSACTION_UPDATE(),
            payload = Json.encodeToString(payload),
            arbitraryId = id
          )
        )

        val existingTransaction = transactionsDao.getByArbitraryTransactionId(id)
        existingTransaction?.let {
          transactionsDao.upsertArbitraryTransaction(
            existingTransaction.copy(
              arbitraryId = id,
              categoryId = model.categoryId,
              amount = model.amount,
              transactionDateMillis = compositeDate(model.date, model.time).parseMillis(),
              updatedAt = currentDateTimeAsInstantString(),
              comment = model.comment,
              isIncome = categoryDao.getCategoryById(model.categoryId)?.isIncome ?: false
            )
          )
        }
      }
      else {
        queueDao.upsertToQueue(
          QueueEntity(
            identifier = "${id}_${QueueOperationType.TRANSACTION_UPDATE}",
            type = QueueOperationType.TRANSACTION_UPDATE(),
            payload = Json.encodeToString(payload)
          )
        )

        val existingTransaction = transactionsDao.getByTransactionId(id)
        existingTransaction?.let {
          transactionsDao.upsertTransaction(
            existingTransaction.copy(
              transactionId = id,
              categoryId = model.categoryId,
              amount = model.amount,
              transactionDateMillis = compositeDate(model.date, model.time).parseMillis(),
              updatedAt = currentDateTimeAsInstantString(),
              comment = model.comment,
              isIncome = categoryDao.getCategoryById(model.categoryId)?.isIncome ?: false
            )
          )
        }
      }
    }
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun createTransaction(model: TransactionInfoModel) {
    val accountId = dataStoreManager.getActiveAccount()?.id ?: throw RuntimeException("No active account!")

    val payload = TransactionCreate(
      accountId = accountId,
      categoryId = model.categoryId,
      amount = model.amount,
      transactionDate = compositeDate(model.date, model.time),
      comment = model.comment
    )

    if (NetworkMonitor.networkAvailable.value){
      val response = httpClient
        .post(Transactions()) {
          setBody(payload)
          header("Content-Type", "application/json")
        }
      val httpStatusCode = response.status

      Log.i("CREATE_TRANSACTION_SC", "$httpStatusCode")
      val body = response.body<TransactionCreateResponse>()

      transactionsDao.upsertTransaction(
        TransactionEntity(
          transactionId = body.id,
          categoryId = body.categoryId,
          amount = body.amount,
          transactionDateMillis = body.transactionDate.parseMillis(),
          createdAt = body.createdAt,
          updatedAt = body.updatedAt,
          comment = body.comment ?: "",
          isIncome = categoryDao.getCategoryById(body.categoryId)?.isIncome ?: false
        )
      )
    } else {
      val id = transactionsDao.upsertArbitraryTransaction(
        ArbitraryTransactionEntity(
          categoryId = model.categoryId,
          amount = model.amount,
          transactionDateMillis = compositeDate(model.date, model.time).parseMillis(),
          createdAt = currentDateTimeAsInstantString(),
          updatedAt = currentDateTimeAsInstantString(),
          comment = model.comment,
          isIncome = categoryDao.getCategoryById(model.categoryId)?.isIncome ?: false
        )
      )

      queueDao.upsertToQueue(
        QueueEntity(
          identifier = "arbitrary_${id}_${QueueOperationType.TRANSACTION_CREATE}",
          type = QueueOperationType.TRANSACTION_CREATE(),
          payload = Json.encodeToString(payload),
          arbitraryId = id.toInt()
        )
      )
    }
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun loadTransaction(id: Int?, isArbitrary : Boolean?) : TransactionInfoModel {
    id?.let {
      if (NetworkMonitor.networkAvailable.value){
        val response = httpClient
          .get(Transactions.Id(id = id))

        val httpStatusCode = response.status
        Log.i("LOAD_TRANSACTION_SC", "$httpStatusCode")

        if (httpStatusCode.value == 200) {
          val transactionRemote = response.body<TransactionResponse>()

          return TransactionInfoModel(
            categoryId = transactionRemote.category.id,
            amount = transactionRemote.amount,
            date = transactionRemote.transactionDate.getDateFromInstantString(),
            time = transactionRemote.transactionDate.getTimeFromInstantString(),
            comment = transactionRemote.comment ?: "",
            accountName = dataStoreManager.getActiveAccount()?.name
              ?: throw RuntimeException("No active account!"),
            categoryName = transactionRemote.category.name,
            isArbitrary = false
          )
        }

        if (httpStatusCode.value == 404) {
          transactionsDao.deleteByTransactionId(id)
        }
      } else {
        if (isArbitrary == true) {
          val entity = transactionsDao.getByArbitraryTransactionId(id)

          entity?.let {
            return TransactionInfoModel(
              accountName = dataStoreManager.getActiveAccount()?.name
                ?: throw RuntimeException("No active account!"),
              categoryId = entity.categoryId,
              categoryName = categoryDao.getCategoryById(entity.categoryId)?.name ?: "",
              amount = entity.amount,
              date = Instant.fromEpochMilliseconds(entity.transactionDateMillis)
                .toString().getDateFromInstantString(),
              time = Instant.fromEpochMilliseconds(entity.transactionDateMillis)
                .toString().getTimeFromInstantString(),
              comment = entity.comment,
              isArbitrary = true
            )
          }
        } else {
          val entity = transactionsDao.getByTransactionId(id)

          entity?.let {
            return TransactionInfoModel(
              accountName = dataStoreManager.getActiveAccount()?.name
                ?: throw RuntimeException("No active account!"),
              categoryId = entity.categoryId,
              categoryName = categoryDao.getCategoryById(entity.categoryId)?.name ?: "",
              amount = entity.amount,
              date = Instant.fromEpochMilliseconds(entity.transactionDateMillis)
                .toString().getDateFromInstantString(),
              time = Instant.fromEpochMilliseconds(entity.transactionDateMillis)
                .toString().getTimeFromInstantString(),
              comment = entity.comment,
              isArbitrary = false
            )
          }
        }
      }
    }

    return TransactionInfoModel(
      accountName = dataStoreManager.getActiveAccount()?.name
        ?: throw RuntimeException("No active account!"),
      isArbitrary = true
    )
  }

  override suspend fun deleteTransaction(id: Int, isArbitrary: Boolean?) {
    if (NetworkMonitor.networkAvailable.value && isArbitrary != true){
      val response = httpClient
        .delete(Transactions.Id(id = id))

      val httpStatusCode = response.status
      Log.i("LOAD_TRANSACTION_SC", "$httpStatusCode")

      transactionsDao.deleteByTransactionId(id)
    } else {
      if (isArbitrary == true) {
        val entity = transactionsDao.getByArbitraryTransactionId(id)
        entity?.let { transactionsDao.deleteArbitraryTransaction(entity) }
      }
      else {
        queueDao.upsertToQueue(
          QueueEntity(
            identifier = "${id}_${QueueOperationType.TRANSACTION_DELETE}",
            type = QueueOperationType.TRANSACTION_DELETE(),
            payload = Json.encodeToString(TransactionDelete(id)),
          )
        )

        transactionsDao.deleteByTransactionId(id)
      }
    }
  }
}
