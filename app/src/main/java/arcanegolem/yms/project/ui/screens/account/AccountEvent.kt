package arcanegolem.yms.project.ui.screens.account

sealed class AccountEvent {
  data object LoadAccount : AccountEvent()
}
