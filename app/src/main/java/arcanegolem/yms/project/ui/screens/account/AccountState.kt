package arcanegolem.yms.project.ui.screens.account

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError

sealed class AccountState {
  data object Idle : AccountState()
  data object Loading : AccountState()
  data class Target(val result : AccountModel) : AccountState()
  data class Error(val error : YMSError) : AccountState()
}