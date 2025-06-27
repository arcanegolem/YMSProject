package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository

/**
 * Этот юзкейс используется для получения первого аккаунта если нет активного, потому что сейчас
 * возникли непонятки с созданием аккаунта и его сменой он пока используется везде вместо [LoadAccountUseCase]
 * которой грузит аккаунт по id, однако когда (или если :D) будет реализована смена и создание аккаунта (счета)
 * то он будет использовваться только при первом запуске приложения чтобы хотя бы какой-то счет был на месте. см [AccountModel]
 *
 * @param accountRepository имплементация [AccountRepository]
 */
class LoadFirstAccountUseCase(
  private val accountRepository: AccountRepository
) {
  suspend fun execute() : AccountModel {
    return accountRepository.loadFirstAccount()
  }
}
