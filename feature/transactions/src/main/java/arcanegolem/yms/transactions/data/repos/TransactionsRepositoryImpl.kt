package arcanegolem.yms.transactions.data.repos

import arcanegolem.yms.account.domain.usecases.LoadAccountRemoteUseCase
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.utils.formatCash
import arcanegolem.yms.core.data.utils.parseMillis
import arcanegolem.yms.core.data.utils.toDateString
import arcanegolem.yms.transactions.data.remote.api.Transactions
import arcanegolem.yms.transactions.data.remote.models.TransactionResponse
import arcanegolem.yms.transactions.domain.models.TransactionModel
import arcanegolem.yms.transactions.domain.models.TransactionsTotaledModel
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
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
  private val loadAccountRemoteUseCase: LoadAccountRemoteUseCase
) : TransactionsRepository {
  // Да тут функции больше 20 строк, но увы красивое форматирование и преобразование данных требуют
  // жертв, проверяющие не бейте :)

  @OptIn(ExperimentalTime::class)
  override suspend fun loadExpenses(
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    val periodStartFormatted = periodStartMillis?.toDateString()
    val periodEndFormatted = periodEndMillis?.toDateString()

    val accountId : Int
    val currency : String

    var cachedAccountInfo = dataStoreManager.getActiveAccount()

    if (cachedAccountInfo != null) {
      accountId = cachedAccountInfo.id
      currency = cachedAccountInfo.currency
    } else {
      loadAccountRemoteUseCase.execute()
      cachedAccountInfo = dataStoreManager.getActiveAccount()
      if (cachedAccountInfo != null) {
        accountId = cachedAccountInfo.id
        currency = cachedAccountInfo.currency
      } else throw RuntimeException("Failure loading account from remote!")
    }

    val response = httpClient
      .get(
        Transactions.Account.ById.Period(
          Transactions.Account.ById(accountId = accountId),
          startDate = periodStartFormatted,
          endDate = periodEndFormatted
        )
      ).body<List<TransactionResponse>>()

    val expenses = response
      .filter { !it.category.isIncome }
      .sortedBy { Instant.parse(it.createdAt) }
      .reversed()

    val expensesSum = expenses
      .map { it.amount.toFloat() }
      .sum()
      .toString()
      .formatCash(currency)

    return TransactionsTotaledModel(
      total = expensesSum,
      transactions = expenses.map { transactionRemote ->
        TransactionModel(
          id = transactionRemote.id,
          emoji = transactionRemote.category.emoji,
          label = transactionRemote.category.name,
          comment = transactionRemote.comment,
          amountFormatted = transactionRemote.amount.formatCash(currency),
          dateTimeMillis = transactionRemote.transactionDate.parseMillis()
        )
      }
    )
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun loadIncomes(
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    val periodStartFormatted = periodStartMillis?.toDateString()
    val periodEndFormatted = periodEndMillis?.toDateString()

    val accountId : Int
    val currency : String

    var cachedAccountInfo = dataStoreManager.getActiveAccount()

    if (cachedAccountInfo != null) {
      accountId = cachedAccountInfo.id
      currency = cachedAccountInfo.currency
    } else {
      loadAccountRemoteUseCase.execute()
      cachedAccountInfo = dataStoreManager.getActiveAccount()
      if (cachedAccountInfo != null) {
        accountId = cachedAccountInfo.id
        currency = cachedAccountInfo.currency
      } else throw RuntimeException("Failure loading account from remote!")
    }

    val response = httpClient
      .get(
        Transactions.Account.ById.Period(
          Transactions.Account.ById(accountId = accountId),
          startDate = periodStartFormatted,
          endDate = periodEndFormatted
        )
      ).body<List<TransactionResponse>>()

    val incomes = response
      .filter { it.category.isIncome }
      .sortedBy { Instant.parse(it.createdAt) }
      .reversed()

    val incomesSum = incomes
      .map { it.amount.toFloat() }
      .sum()
      .toString()
      .formatCash(currency)

    return TransactionsTotaledModel(
      total = incomesSum,
      transactions = incomes.map { transactionRemote ->
        TransactionModel(
          id = transactionRemote.id,
          emoji = transactionRemote.category.emoji,
          label = transactionRemote.category.name,
          comment = transactionRemote.comment,
          amountFormatted = transactionRemote.amount.formatCash(currency),
          dateTimeMillis = transactionRemote.transactionDate.parseMillis()
        )
      }
    )
  }
}
