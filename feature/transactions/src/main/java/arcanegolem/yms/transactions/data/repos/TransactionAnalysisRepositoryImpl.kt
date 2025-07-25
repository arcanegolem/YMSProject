package arcanegolem.yms.transactions.data.repos

import arcanegolem.yms.account.domain.usecases.LoadAccountRemoteUseCase
import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.dao.TransactionsDao
import arcanegolem.yms.core.data.database.entities.TransactionEntity
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.utils.formatCash
import arcanegolem.yms.core.data.utils.monthStartMillis
import arcanegolem.yms.core.data.utils.parseMillis
import arcanegolem.yms.core.data.utils.toDateStringYYYYMMDD
import arcanegolem.yms.core.data.utils.todayMillis
import arcanegolem.yms.core.utils.NetworkMonitor
import arcanegolem.yms.core.domain.models.TransactionIntermediateModel
import arcanegolem.yms.core.data.remote.api.Transactions
import arcanegolem.yms.core.data.remote.models.TransactionResponse
import arcanegolem.yms.transactions.domain.models.CategoryTotalModel
import arcanegolem.yms.transactions.domain.models.TransactionAnalysisModel
import arcanegolem.yms.transactions.domain.repos.TransactionsAnalysisRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionAnalysisRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val dataStoreManager: DataStoreManager,
  private val transactionsDao: TransactionsDao,
  private val loadAccountRemoteUseCase: LoadAccountRemoteUseCase,
  private val categoryDao: CategoryDao,
) : TransactionsAnalysisRepository {
  override suspend fun loadAnalysisForPeriod(
    periodStartMillis: Long?,
    periodEndMillis: Long?,
    isIncome: Boolean
  ): Flow<TransactionAnalysisModel> {
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
        Pair(transactionEntities, accountInfo?.currency)
      }.map { (transactionEntities, currency) ->
        val total = transactionEntities
          .map { it.amount.toFloat() }
          .sum()

        val categoryIdToTotalMap = buildMap {
          transactionEntities.forEach { entity ->
            put(entity.categoryId, getOrPut(entity.categoryId) { 0f } + entity.amount.toFloat())
          }
        }

        TransactionAnalysisModel(
          isIncome = isIncome,
          total = total
            .toString()
            .formatCash(currency ?: ""),
          periodStart = periodStartMillis ?: monthStartMillis(),
          periodEnd = periodEndMillis ?: todayMillis(),
          categoriesData = categoryIdToTotalMap.map { (categoryId, categoryTotal) ->
            val relCategory = categoryDao.getCategoryById(categoryId) ?: throw RuntimeException("No category for id $categoryId")
            CategoryTotalModel(
              total = categoryTotal.toString().formatCash(currency ?: ""),
              percentage = "${"%.2f".format((categoryTotal / total) * 100)}%",
              category = CategoryModel(
                id = categoryId,
                name = relCategory.name,
                emoji = relCategory.emoji,
                isIncome = relCategory.isIncome
              )
            )
          }
        )
      }
  }
}