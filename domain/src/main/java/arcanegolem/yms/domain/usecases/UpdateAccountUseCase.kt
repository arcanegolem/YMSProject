package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute(model : AccountModel) {
    accountRepository.updateAccount(model)
  }
}