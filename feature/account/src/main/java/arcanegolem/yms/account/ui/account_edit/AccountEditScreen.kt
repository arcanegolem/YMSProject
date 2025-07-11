package arcanegolem.yms.account.ui.account_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.account.navigation.AccountEdit
import arcanegolem.yms.account.ui.account_edit.state_handlers.TargetAccountEditState
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState

@Composable
fun AccountEditScreen(
  route : AccountEdit,
  state : State<AccountEditState>,
  eventProcessor : (AccountEditEvent) -> Unit
) {
  when (val s = state.value) {
    AccountEditState.Idle -> eventProcessor(
      AccountEditEvent.InitialLoad(route.name, route.balance, route.currency)
    )
    AccountEditState.Loading -> LoadingState()
    is AccountEditState.Target -> TargetAccountEditState(s, eventProcessor)
    is AccountEditState.Error -> ErrorState(s.error)
  }
}
