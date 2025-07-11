package arcanegolem.yms.account.domain.repos

import arcanegolem.yms.account.domain.models.AccountModel
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
  /**
   * Загружает данные о счете по идентификатору
   *
   * NOTE: Не используется пока, так как не нужно реализовывать смену аккаунтов (пока что :D)
   *
   * @param accountId идентификатор счета
   * @return данные о счете в виде [AccountModel]
   */
  suspend fun loadAccount(accountId : Int) : AccountModel

  /**
   * Отдельный метод для подгрузки с бека информации о первом счете и кешировании его в DataStore
   */
  suspend fun loadFirstRemoteAccount()

  /**
   * Загружает данные о первом счете
   *
   * @return данные о счете в виде [Flow] из [AccountModel]
   */
  suspend fun getAccount() : Flow<AccountModel?>

  /**
   * Обновляет данные о счете
   *
   * @param model измененная модель счета в виде [AccountModel]
   */
  suspend fun updateAccount(model : AccountModel)
}
