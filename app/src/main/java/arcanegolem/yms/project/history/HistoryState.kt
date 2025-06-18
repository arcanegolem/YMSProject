package arcanegolem.yms.project.history

import arcanegolem.yms.domain.models.HistoryModel

sealed class HistoryState {
  data object Idle : HistoryState()
  data object Loading : HistoryState()
  data class Target(val result : HistoryModel) : HistoryState()
  data class Error(val error : Throwable) : HistoryState()
}