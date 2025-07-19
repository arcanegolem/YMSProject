package arcanegolem.yms.transactions.ui.analysis

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.transactions.navigation.Analysis
import arcanegolem.yms.transactions.ui.analysis.state_handlers.TargetAnalysisState

@Composable
fun AnalysisScreen(
  route : Analysis,
  state: State<AnalysisState>,
  eventProcessor: (AnalysisEvent) -> Unit
) {
  when (val s = state.value) {
    AnalysisState.Idle -> eventProcessor(AnalysisEvent.LoadAnalysisForPeriod(route.isIncome))
    AnalysisState.Loading -> LoadingState()
    is AnalysisState.Target -> TargetAnalysisState(s, eventProcessor)
    is AnalysisState.Error -> ErrorState(s.error)
  }
}