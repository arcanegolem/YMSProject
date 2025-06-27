package arcanegolem.yms.data.repos

import arcanegolem.yms.data.datastore.DataStoreManager
import arcanegolem.yms.data.datastore.models.AccountInfoModel
import arcanegolem.yms.data.remote.models.AccountResponse
import arcanegolem.yms.data.remote.api.Accounts
import arcanegolem.yms.data.remote.models.Account
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.data.util.formatCurrency
import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

/**
 * Реальная имплементация репозитория для работы с счетами
 *
 * @param httpClient Ktor http-клиент
 * @param dataStoreManager хелпер для датастора с данными активного счета
 */
internal class AccountRepositoryImpl(
  private val httpClient: HttpClient,
  private val dataStoreManager : DataStoreManager
) : AccountRepository {
  override suspend fun loadAccount(accountId: Int): AccountModel {
    // Тут пока AccountResponse хотя должно быть AccountHistoryResponse из Swagger, но так как графика пока что нет оставил так
    val response = httpClient.get(Accounts.Id(id = accountId)).body<AccountResponse>()

    return AccountModel(
      balance = response.balance.formatCash(response.currency),
      currency = response.currency.formatCurrency()
    )
  }

  override suspend fun loadFirstAccount(): AccountModel {
    val response = httpClient.get(Accounts()).body<List<Account>>()
    val first = response.first()

    dataStoreManager.updateActiveAccount(
      AccountInfoModel(
        id = first.id,
        currency = first.currency
      )
    )

    return AccountModel(
      balance = first.balance.formatCash(first.currency),
      currency = first.currency.formatCurrency()
    )
  }
}