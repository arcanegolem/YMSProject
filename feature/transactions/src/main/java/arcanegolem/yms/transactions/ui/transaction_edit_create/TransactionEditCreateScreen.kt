package arcanegolem.yms.transactions.ui.transaction_edit_create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.transactions.navigation.TransactionEditCreate
import arcanegolem.yms.transactions.ui.transaction_edit_create.state_handlers.TargetTransactionEditCreateState

@Composable
fun TransactionEditCreateScreen(
  route : TransactionEditCreate,
  state : State<TransactionEditCreateState>,
  eventProcessor : (TransactionEditCreateEvent) -> Unit,
  onScreenExit : () -> Unit,
  isEdit : Boolean
) {
  when (val s = state.value) {
    TransactionEditCreateState.Idle -> {
      eventProcessor(
        if (route.transactionId != null) {
          TransactionEditCreateEvent.InitialLoad(route.transactionId, route.isIncome)
        } else {
          TransactionEditCreateEvent.InitialBlank(route.isIncome)
        }
      )
    }
    TransactionEditCreateState.Loading -> LoadingState()
    is TransactionEditCreateState.Target -> TargetTransactionEditCreateState(s, eventProcessor, onScreenExit, isEdit)
    is TransactionEditCreateState.Error -> ErrorState(s.error)
  }
}