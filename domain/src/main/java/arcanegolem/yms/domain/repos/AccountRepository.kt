package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.AccountModel

interface AccountRepository {
  suspend fun loadAccount(accountId : Int) : AccountModel
  suspend fun loadFirstAccount() : AccountModel
}