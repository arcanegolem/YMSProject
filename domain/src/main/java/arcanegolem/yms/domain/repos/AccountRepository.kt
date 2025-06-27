package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.AccountModel

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
   * Загружает данные о первом счете
   *
   * @return данные о счете в виде [AccountModel]
   */
  suspend fun loadFirstAccount() : AccountModel
}
