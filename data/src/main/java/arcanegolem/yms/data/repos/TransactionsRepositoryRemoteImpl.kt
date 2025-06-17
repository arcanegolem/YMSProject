package arcanegolem.yms.data.repos

import arcanegolem.yms.data.models.TransactionResponse
import arcanegolem.yms.data.remote.api.Transactions
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.data.util.toDateString
import arcanegolem.yms.data.util.toFormattedDateTime
import arcanegolem.yms.domain.models.TransactionModel
import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.datetime.Instant

class TransactionsRepositoryRemoteImpl(
  private val httpClient: HttpClient
) : TransactionsRepository {
  override suspend fun loadExpenses(
    accountId: Int,
    currency: String,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    val periodStartFormatted = periodStartMillis?.toDateString()
    val periodEndFormatted = periodEndMillis?.toDateString()

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
          dateTimeFormatted = transactionRemote.transactionDate.toFormattedDateTime()
        )
      }
    )
  }

  override suspend fun loadIncomes(
    accountId: Int,
    currency: String,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    val periodStartFormatted = periodStartMillis?.toDateString()
    val periodEndFormatted = periodEndMillis?.toDateString()

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
          dateTimeFormatted = transactionRemote.transactionDate.toFormattedDateTime()
        )
      }
    )
  }
}