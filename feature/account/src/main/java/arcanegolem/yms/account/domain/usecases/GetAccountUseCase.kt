package arcanegolem.yms.account.domain.usecases

import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.account.domain.repos.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Этот юзкейс используется для получения первого аккаунта если нет активного, потому что сейчас
 * возникли непонятки с созданием аккаунта и его сменой он пока используется везде вместо
 * подгрузки по id, однако когда (или если :D) будет реализована смена и создание аккаунта (счета)
 * то он будет использовваться только при первом запуске приложения чтобы хотя бы какой-то счет был
 * на месте. см [AccountModel]
 *
 * @param accountRepository имплементация [AccountRepository]
 */
class GetAccountUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute() : Flow<AccountModel?> {
    return accountRepository.getAccount()
  }
}
