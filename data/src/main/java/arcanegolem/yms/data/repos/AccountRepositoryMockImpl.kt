package arcanegolem.yms.data.repos

import arcanegolem.yms.data.mock.mockAccount
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.data.util.formatCurrency
import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository

class AccountRepositoryMockImpl : AccountRepository {
  override suspend fun loadAccount(accountId: Int): AccountModel {
    val loadedData = mockAccount // Тут пока не очень верная data-модель
    return AccountModel(
      balance = loadedData.balance.formatCash(loadedData.currency),
      currency = loadedData.currency.formatCurrency()
    )
  }
}