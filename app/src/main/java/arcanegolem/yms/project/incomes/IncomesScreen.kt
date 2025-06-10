package arcanegolem.yms.project.incomes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import arcanegolem.yms.project.common.state_handlers.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState
import arcanegolem.yms.project.incomes.state_handlers.TargetIncomesState
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncomesScreen(
  viewModel: IncomesViewModel = koinViewModel()
) {
  val state = viewModel.state.collectAsState()

  when (val s = state.value) {
    IncomesState.Idle -> viewModel.processEvent(IncomesEvent.LoadIncomes)
    IncomesState.Loading -> LoadingState()
    is IncomesState.Target -> TargetIncomesState(s)
    is IncomesState.Error -> ErrorState(s.error)
  }
}