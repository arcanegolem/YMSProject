package arcanegolem.yms.data.repos

import arcanegolem.yms.data.mock.mockTransactions
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.domain.models.TransactionModel
import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository

class TransactionsRepositoryMockImpl : TransactionsRepository {
  override suspend fun loadExpenses(
    accountId: Int,
    currency: String,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    // Сюда потом подгрузка транзакций по id аккаунта
    val loadedData = mockTransactions
    val transactionsMapped = loadedData
      .filter { !it.category.isIncome }
      .map { transactionRemote ->
        TransactionModel(
          id = transactionRemote.id,
          emoji = transactionRemote.category.emoji,
          label = transactionRemote.category.name,
          comment = transactionRemote.comment,
          amountFormatted = transactionRemote.amount.formatCash(currency)
        )
      }

    val total = loadedData
      .map { it.amount.toFloat() }
      .sum()
      .toString()
      .formatCash(currency)

    return TransactionsTotaledModel(total, transactionsMapped)
  }

  override suspend fun loadIncomes(
    accountId: Int,
    currency: String,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    // Сюда потом подгрузка транзакций по id аккаунта
    val loadedData = mockTransactions
    val transactionsMapped = loadedData
      .filter { it.category.isIncome }
      .map { transactionRemote ->
        TransactionModel(
          id = transactionRemote.id,
          emoji = transactionRemote.category.emoji,
          label = transactionRemote.category.name,
          comment = transactionRemote.comment,
          amountFormatted = transactionRemote.amount.formatCash(currency)
        )
      }

    val total = loadedData
      .map { it.amount.toFloat() }
      .sum()
      .toString()
      .formatCash(currency)

    return TransactionsTotaledModel(total, transactionsMapped)
  }
}