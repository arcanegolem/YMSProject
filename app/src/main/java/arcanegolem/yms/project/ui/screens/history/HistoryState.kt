package arcanegolem.yms.project.ui.screens.history

import arcanegolem.yms.domain.models.HistoryModel
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError

sealed class HistoryState {
  data object Idle : HistoryState()
  data object Loading : HistoryState()
  data class Target(val result : HistoryModel) : HistoryState()
  data class Error(val error : YMSError) : HistoryState()
}