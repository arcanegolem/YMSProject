package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.repos.AccountRepository

class LoadAccountRemoteUseCase(
  private val accountRepository: AccountRepository
) {
  suspend fun execute() {
    accountRepository.loadFirstRemoteAccount()
  }
}