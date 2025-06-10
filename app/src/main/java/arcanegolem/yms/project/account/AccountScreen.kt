package arcanegolem.yms.project.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import arcanegolem.yms.project.account.state_handlers.TargetAccountState
import arcanegolem.yms.project.common.state_handlers.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(
  viewModel : AccountViewModel = koinViewModel()
) {
  val state = viewModel.state.collectAsState()

  when (val s = state.value) {
    AccountState.Idle -> viewModel.processEvent(AccountEvent.LoadAccount)
    AccountState.Loading -> LoadingState()
    is AccountState.Target -> TargetAccountState(s)
    is AccountState.Error -> ErrorState(s.error)
  }
}