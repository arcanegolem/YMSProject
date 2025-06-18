package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository

class LoadIncomesUseCase(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel {
    return transactionsRepository.loadIncomes(periodStartMillis, periodEndMillis)
  }
}