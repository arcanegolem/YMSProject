package arcanegolem.yms.project.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.project.common.state_handlers.error.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState
import arcanegolem.yms.project.history.state_handlers.TargetHistoryState
import arcanegolem.yms.project.navigation.routes.History

@Composable
fun HistoryScreen(
  route : History,
  state : State<HistoryState>,
  eventProcessor : (HistoryEvent) -> Unit
) {
  when (val s = state.value) {
    HistoryState.Idle -> eventProcessor(HistoryEvent.LoadTransactionsForPeriod(route.isIncome))
    HistoryState.Loading -> LoadingState()
    is HistoryState.Target -> TargetHistoryState(s, eventProcessor)
    is HistoryState.Error -> ErrorState(s.error)
  }
}