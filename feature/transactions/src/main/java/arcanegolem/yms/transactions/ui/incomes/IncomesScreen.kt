package arcanegolem.yms.transactions.ui.incomes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavController
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.transactions.ui.incomes.state_handlers.TargetIncomesState

@Composable
fun IncomesScreen(
  navController: NavController,
  state : State<IncomesState>,
  eventProcessor : (IncomesEvent) -> Unit
) {
  when (val s = state.value) {
    IncomesState.Idle -> eventProcessor(IncomesEvent.LoadIncomes)
    IncomesState.Loading -> LoadingState()
    is IncomesState.Target -> TargetIncomesState(navController, s)
    is IncomesState.Error -> ErrorState(s.error)
  }
}
