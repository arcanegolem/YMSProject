package arcanegolem.yms.project.incomes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.project.common.state_handlers.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState
import arcanegolem.yms.project.incomes.state_handlers.TargetIncomesState

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