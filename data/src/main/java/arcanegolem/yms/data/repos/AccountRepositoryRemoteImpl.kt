package arcanegolem.yms.data.repos

import arcanegolem.yms.data.remote.models.AccountResponse
import arcanegolem.yms.data.remote.api.Accounts
import arcanegolem.yms.data.util.formatCash
import arcanegolem.yms.data.util.formatCurrency
import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.repos.AccountRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class AccountRepositoryRemoteImpl(
  private val httpClient: HttpClient
) : AccountRepository {
  override suspend fun loadAccount(accountId: Int): AccountModel {
    // Тут пока AccountResponse хотя должно быть AccountHistoryResponse из Swagger, но так как графика пока что нет оставил так
    val response = httpClient.get(Accounts.Id(id = accountId)).body<AccountResponse>()

    return AccountModel(
      balance = response.balance.formatCash(response.currency),
      currency = response.currency.formatCurrency()
    )
  }
}