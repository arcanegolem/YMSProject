package arcanegolem.yms.account.ui.account

import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError

sealed class AccountState {
  data object Idle : AccountState()
  data object Loading : AccountState()
  data class Target(val result : AccountModel) : AccountState()
  data class Error(val error : YMSError) : AccountState()
}
