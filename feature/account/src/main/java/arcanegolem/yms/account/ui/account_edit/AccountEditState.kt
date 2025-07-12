package arcanegolem.yms.account.ui.account_edit

import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError

sealed class AccountEditState {
  data object Idle : AccountEditState()
  data object Loading : AccountEditState()
  data class Target(val result : AccountModel) : AccountEditState()
  data class Error(val error : YMSError) : AccountEditState()
}
