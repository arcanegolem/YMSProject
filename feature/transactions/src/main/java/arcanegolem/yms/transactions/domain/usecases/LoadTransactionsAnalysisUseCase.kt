package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.transactions.domain.models.TransactionAnalysisModel
import arcanegolem.yms.transactions.domain.repos.TransactionsAnalysisRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadTransactionsAnalysisUseCase @Inject constructor(
  private val transactionsAnalysisRepository: TransactionsAnalysisRepository
) {
  suspend fun execute(
    isIncome : Boolean,
    periodStart : Long? = null,
    periodEnd : Long? = null
  ) : Flow<TransactionAnalysisModel> {
    return transactionsAnalysisRepository.loadAnalysisForPeriod(periodStart, periodEnd, isIncome)
  }
}