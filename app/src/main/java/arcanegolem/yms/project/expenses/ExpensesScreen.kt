package arcanegolem.yms.project.expenses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import arcanegolem.yms.project.common.state_handlers.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState
import arcanegolem.yms.project.expenses.state_handlers.TargetExpensesState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpensesScreen(
  viewModel: ExpensesViewModel = koinViewModel()
) {
  val state = viewModel.state.collectAsState()

  when (val s = state.value) {
    ExpensesState.Idle -> viewModel.processEvent(ExpensesEvent.LoadExpenses)
    ExpensesState.Loading -> LoadingState()
    is ExpensesState.Target -> TargetExpensesState(s)
    is ExpensesState.Error -> ErrorState(s.error)
  }
}