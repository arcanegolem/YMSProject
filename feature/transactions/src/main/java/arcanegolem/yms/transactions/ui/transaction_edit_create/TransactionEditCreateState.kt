package arcanegolem.yms.transactions.ui.transaction_edit_create

import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.models.TransactionInfoModel

sealed class TransactionEditCreateState {
  data object Idle : TransactionEditCreateState()
  data object Loading : TransactionEditCreateState()
  data class Target(
    val result : TransactionInfoModel,
    val sameErrorCounter : Int = 0,
    val transactionSyncError : Throwable? = null,
    val availableCategories : List<CategoryModel>
  ) : TransactionEditCreateState()
  data class Error(val error : YMSError) : TransactionEditCreateState()
}