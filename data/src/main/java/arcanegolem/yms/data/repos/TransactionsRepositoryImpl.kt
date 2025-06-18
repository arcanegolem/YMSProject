package arcanegolem.yms.data.repos

import arcanegolem.yms.data.datastore.DataStoreManager
import arcanegolem.yms.data.datastore.models.AccountInfoModel
import arcanegolem.yms.data.remote.api.Accounts
import arcanegolem.yms.data.remote.api.Transactions
import arcanegolem.yms.data.remote.models.Account
import arcanegolem.yms.data.remote.models.TransactionResponse
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.data.util.parseMillis
import arcanegolem.yms.data.util.toDateString
import arcanegolem.yms.domain.models.TransactionModel
import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.datetime.Instant

class TransactionsRepositoryImpl(
  private val httpClient: HttpClient,
  private val dataStoreManager: DataStoreManager
) : TransactionsRepository {
  override suspend fun loadExpenses(
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    val periodStartFormatted = periodStartMillis?.toDateString()
    val periodEndFormatted = periodEndMillis?.toDateString()

    val accountId : Int
    val currency : String

    val cachedAccountInfo = dataStoreManager.getActiveAccount()

    if (cachedAccountInfo != null) {
      accountId = cachedAccountInfo.id
      currency = cachedAccountInfo.currency
    } else {
      val remoteAccount = httpClient.get(Accounts()).body<List<Account>>().first()
      val remoteInfo = AccountInfoModel(remoteAccount.id, remoteAccount.currency)
      dataStoreManager.updateActiveAccount(remoteInfo)
      accountId = remoteInfo.id
      currency = remoteInfo.currency
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

  override suspend fun loadIncomes(
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    val periodStartFormatted = periodStartMillis?.toDateString()
    val periodEndFormatted = periodEndMillis?.toDateString()

    val accountId : Int
    val currency : String

    val cachedAccountInfo = dataStoreManager.getActiveAccount()

    if (cachedAccountInfo != null) {
      accountId = cachedAccountInfo.id
      currency = cachedAccountInfo.currency
    } else {
      val remoteAccount = httpClient.get(Accounts()).body<List<Account>>().first()
      val remoteInfo = AccountInfoModel(remoteAccount.id, remoteAccount.currency)
      dataStoreManager.updateActiveAccount(remoteInfo)
      accountId = remoteInfo.id
      currency = remoteInfo.currency
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