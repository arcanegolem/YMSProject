package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository

class UpdateAccountUseCase(
  private val accountRepository: AccountRepository
) {
  suspend fun execute(model : AccountModel) {
    accountRepository.updateAccount(model)
  }
}