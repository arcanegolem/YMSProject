package arcanegolem.yms.project.ui.screens.account_edit

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError

sealed class AccountEditState {
  data object Idle : AccountEditState()
  data object Loading : AccountEditState()
  data class Target(val result : AccountModel) : AccountEditState()
  data class Error(val error : YMSError) : AccountEditState()
}