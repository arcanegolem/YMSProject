package arcanegolem.yms.project.ui.screens.account_edit

sealed class AccountEditEvent {
  data class UpdateAccountName(val new: String) : AccountEditEvent()
  data class UpdateAccountCurrency(val new: String) : AccountEditEvent()
  data class UpdateAccountBalance(val new : String) : AccountEditEvent()
  data class InitialLoad(
    val name : String,
    val balance : String,
    val currency : String
  ) : AccountEditEvent()
  data class ConsumeUpdates(val onSuccess : () -> Unit) : AccountEditEvent()
}
