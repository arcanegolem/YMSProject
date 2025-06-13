package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository

class LoadAccountUseCase(
  private val accountRepository: AccountRepository
) {
  suspend fun execute(accountId : Int) : AccountModel {
    return accountRepository.loadAccount(accountId)
  }
}