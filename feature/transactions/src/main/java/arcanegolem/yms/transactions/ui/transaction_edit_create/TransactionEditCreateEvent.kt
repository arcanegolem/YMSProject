package arcanegolem.yms.transactions.ui.transaction_edit_create

import arcanegolem.yms.core.ui.components.time_picker.PickedTime

sealed class TransactionEditCreateEvent {
  data class InitialLoad(val transactionId : Int, val isIncome: Boolean, val isArbitrary : Boolean?) : TransactionEditCreateEvent()
  data class InitialBlank(val isIncome : Boolean) : TransactionEditCreateEvent()
  data class UpdateTransactionCategory(val id : Int, val name : String) : TransactionEditCreateEvent()
  data class UpdateTransactionAmount(val new : String) : TransactionEditCreateEvent()
  data class UpdateTransactionDate(val new : Long?) : TransactionEditCreateEvent()
  data class UpdateTransactionTime(val new : PickedTime) : TransactionEditCreateEvent()
  data class UpdateTransactionComment(val new : String) : TransactionEditCreateEvent()
  data class DeleteTransaction(val onSuccess: () -> Unit) : TransactionEditCreateEvent()
  data class ConsumeUpdates(val onSuccess : () -> Unit) : TransactionEditCreateEvent()
}