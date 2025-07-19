package arcanegolem.yms.transactions.domain.repos

import arcanegolem.yms.transactions.domain.models.TransactionAnalysisModel
import kotlinx.coroutines.flow.Flow

interface TransactionsAnalysisRepository {
  suspend fun loadAnalysisForPeriod(
    periodStartMillis: Long?,
    periodEndMillis: Long?,
    isIncome : Boolean
  ) : Flow<TransactionAnalysisModel>
}