package arcanegolem.yms.project.ui.screens.expenses

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError

sealed class ExpensesState {
  data object Idle : ExpensesState()
  data object Loading : ExpensesState()
  data class Target(val result : TransactionsTotaledModel) : ExpensesState()
  data class Error(val error : YMSError) : ExpensesState()
}
