package arcanegolem.yms.transactions.ui.analysis

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.models.TransactionAnalysisModel

sealed class AnalysisState {
  data object Idle : AnalysisState()
  data object Loading : AnalysisState()
  data class Target(val result : TransactionAnalysisModel) : AnalysisState()
  data class Error(val error : YMSError) : AnalysisState()
}