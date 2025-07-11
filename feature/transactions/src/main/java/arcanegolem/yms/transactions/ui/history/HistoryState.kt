package arcanegolem.yms.transactions.ui.history

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.models.TransactionHistoryModel

sealed class HistoryState {
  data object Idle : HistoryState()
  data object Loading : HistoryState()
  data class Target(val result : TransactionHistoryModel) : HistoryState()
  data class Error(val error : YMSError) : HistoryState()
}
