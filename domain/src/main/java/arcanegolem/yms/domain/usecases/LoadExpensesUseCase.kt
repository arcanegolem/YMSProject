package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository

class LoadExpensesUseCase(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(accountId : Int, currency: String): TransactionsTotaledModel {
    return transactionsRepository.loadExpenses(accountId, currency)
  }
}