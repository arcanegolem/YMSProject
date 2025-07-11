package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.repos.AccountRepository
import javax.inject.Inject

class LoadAccountRemoteUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute() {
    accountRepository.loadFirstRemoteAccount()
  }
}