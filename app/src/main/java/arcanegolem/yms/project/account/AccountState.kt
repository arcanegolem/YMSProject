package arcanegolem.yms.project.account

import arcanegolem.yms.domain.models.AccountModel

sealed class AccountState {
  data object Idle : AccountState()
  data object Loading : AccountState()
  data class Target(val result : AccountModel) : AccountState()
  data class Error(val error : Throwable) : AccountState()
}