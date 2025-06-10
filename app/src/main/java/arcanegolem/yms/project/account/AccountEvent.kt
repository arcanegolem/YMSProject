package arcanegolem.yms.project.account

sealed class AccountEvent {
  data object LoadAccount : AccountEvent()
}