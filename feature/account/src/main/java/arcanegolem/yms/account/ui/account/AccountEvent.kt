package arcanegolem.yms.account.ui.account

sealed class AccountEvent {
  data object LoadAccount : AccountEvent()
}
