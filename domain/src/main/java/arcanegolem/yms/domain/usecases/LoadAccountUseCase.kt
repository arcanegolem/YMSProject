package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository
import javax.inject.Inject

/**
 * Юзкейс для получения информации о счете по accountId см. [AccountModel]
 *
 * @param accountRepository имплементация [AccountRepository]
 */
class LoadAccountUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute(accountId : Int) : AccountModel {
    return accountRepository.loadAccount(accountId)
  }
}
