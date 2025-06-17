package arcanegolem.yms.data.repos

import arcanegolem.yms.data.mock.mockTransactions
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.data.util.toFormattedDateTime
import arcanegolem.yms.domain.models.TransactionModel
import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository
import kotlinx.datetime.Instant

class TransactionsRepositoryMockImpl : TransactionsRepository {
  override suspend fun loadExpenses(
    accountId: Int,
    currency: String,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    // Сюда потом подгрузка транзакций по id аккаунта
    val loadedData = mockTransactions

    val expenses = loadedData
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
    // Сюда потом подгрузка транзакций по id аккаунта
    val loadedData = mockTransactions

    val incomes = loadedData
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
          dateTimeFormatted = transactionRemote.transactionDate.toFormattedDateTime()
        )
      }
    )
  }
}