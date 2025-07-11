package arcanegolem.yms.account.domain.usecases

import arcanegolem.yms.account.domain.repos.AccountRepository
import javax.inject.Inject

class LoadAccountRemoteUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute() {
    accountRepository.loadFirstRemoteAccount()
  }
}