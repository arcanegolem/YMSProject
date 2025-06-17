package arcanegolem.yms.project.history

import arcanegolem.yms.domain.models.TransactionsTotaledModel

sealed class HistoryState {
  data object Idle : HistoryState()
  data object Loading : HistoryState()
  data class Target(val result : TransactionsTotaledModel) : HistoryState()
  data class Error(val error : Throwable) : HistoryState()
}