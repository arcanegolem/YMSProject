package arcanegolem.yms.account.domain.usecases

import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.account.domain.repos.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute(model : AccountModel) {
    accountRepository.updateAccount(model)
  }
}