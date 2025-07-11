package arcanegolem.yms.transactions.ui.expenses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.transactions.ui.expenses.state_handlers.TargetExpensesState

@Composable
fun ExpensesScreen(
  state : State<ExpensesState>,
  eventProcessor : (ExpensesEvent) -> Unit
) {
  when (val s = state.value) {
    ExpensesState.Idle -> eventProcessor(ExpensesEvent.LoadExpenses)
    ExpensesState.Loading -> LoadingState()
    is ExpensesState.Target -> TargetExpensesState(s)
    is ExpensesState.Error -> ErrorState(s.error)
  }
}
