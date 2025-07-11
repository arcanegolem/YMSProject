package arcanegolem.yms.transactions.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.transactions.navigation.History
import arcanegolem.yms.transactions.ui.history.state_handlers.TargetHistoryState

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
