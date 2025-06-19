package arcanegolem.yms.project.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.project.account.state_handlers.TargetAccountState
import arcanegolem.yms.project.common.state_handlers.error.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState

@Composable
fun AccountScreen(
  state: State<AccountState>,
  eventProcessor: (AccountEvent) -> Unit
) {
  when (val s = state.value) {
    AccountState.Idle -> eventProcessor(AccountEvent.LoadAccount)
    AccountState.Loading -> LoadingState()
    is AccountState.Target -> TargetAccountState(s)
    is AccountState.Error -> ErrorState(s.error)
  }
}