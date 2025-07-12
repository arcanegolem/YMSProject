package arcanegolem.yms.account.ui.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.account.ui.account.state_handlers.TargetAccountState
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState

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
