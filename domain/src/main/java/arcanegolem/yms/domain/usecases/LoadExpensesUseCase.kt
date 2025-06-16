package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository

class LoadExpensesUseCase(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(
    accountId : Int,
    currency: String,
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ): TransactionsTotaledModel {
    return transactionsRepository.loadExpenses(accountId, currency, periodStartMillis, periodEndMillis)
  }
}