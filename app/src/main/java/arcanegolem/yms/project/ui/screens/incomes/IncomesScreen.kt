package arcanegolem.yms.project.ui.screens.incomes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.project.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.project.ui.components.state_handlers.LoadingState
import arcanegolem.yms.project.ui.screens.incomes.state_handlers.TargetIncomesState

@Composable
fun IncomesScreen(
  state : State<IncomesState>,
  eventProcessor : (IncomesEvent) -> Unit
) {
  when (val s = state.value) {
    IncomesState.Idle -> eventProcessor(IncomesEvent.LoadIncomes)
    IncomesState.Loading -> LoadingState()
    is IncomesState.Target -> TargetIncomesState(s)
    is IncomesState.Error -> ErrorState(s.error)
  }
}
