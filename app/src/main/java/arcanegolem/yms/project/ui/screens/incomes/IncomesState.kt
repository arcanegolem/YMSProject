package arcanegolem.yms.project.ui.screens.incomes

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError

sealed class IncomesState {
  data object Idle : IncomesState()
  data object Loading : IncomesState()
  data class Target(val result : TransactionsTotaledModel) : IncomesState()
  data class Error(val error : YMSError) : IncomesState()
}
