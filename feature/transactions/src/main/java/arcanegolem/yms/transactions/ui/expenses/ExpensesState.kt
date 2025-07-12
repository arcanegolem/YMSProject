package arcanegolem.yms.transactions.ui.expenses

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.models.TransactionsTotaledModel

sealed class ExpensesState {
  data object Idle : ExpensesState()
  data object Loading : ExpensesState()
  data class Target(val result : TransactionsTotaledModel) : ExpensesState()
  data class Error(val error : YMSError) : ExpensesState()
}
