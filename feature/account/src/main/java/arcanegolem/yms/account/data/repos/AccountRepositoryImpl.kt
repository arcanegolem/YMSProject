package arcanegolem.yms.account.data.repos

import android.util.Log
import arcanegolem.yms.account.data.remote.api.Accounts
import arcanegolem.yms.account.data.remote.models.Account
import arcanegolem.yms.account.data.remote.models.AccountResponse
import arcanegolem.yms.account.data.remote.models.AccountUpdate
import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.datastore.models.AccountInfoModel
import arcanegolem.yms.core.data.utils.formatCash
import arcanegolem.yms.core.data.utils.formatCashBackwards
import arcanegolem.yms.core.data.utils.formatCurrency
import arcanegolem.yms.core.data.utils.formatCurrencyBackwards
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Реальная имплементация репозитория для работы с счетами
 *
 * @param httpClient Ktor http-клиент
 * @param dataStoreManager хелпер для датастора с данными активного счета
 */
class AccountRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val dataStoreManager : DataStoreManager
) : AccountRepository {
  override suspend fun loadAccount(accountId: Int): AccountModel {
    // Тут пока AccountResponse хотя должно быть AccountHistoryResponse из Swagger,
    // но так как графика пока что нет оставил так
    val response = httpClient.get(Accounts.Id(id = accountId)).body<AccountResponse>()

    return AccountModel(
      name = response.name,
      balance = response.balance.formatCash(response.currency),
      currency = response.currency.formatCurrency()
    )
  }

  override suspend fun loadFirstRemoteAccount() {
    // Тут пока AccountResponse хотя должно быть AccountHistoryResponse из Swagger,
    // но так как графика пока что нет оставил так
    val response = httpClient.get(Accounts()).body<List<Account>>()
    val first = response.first()

    dataStoreManager.updateActiveAccount(
      AccountInfoModel(
        id = first.id,
        currency = first.currency,
        name = first.name,
        balance = first.balance.formatCash(first.currency),
      )
    )
  }

  override suspend fun getAccount(): Flow<AccountModel?> {
    return dataStoreManager.getActiveAccountFlow().map { infoModel ->
      infoModel?.let {
        Log.i("ACC_INFO", it.currency)
        Log.i("ACC_INFO", it.balance)

        AccountModel(
          name = it.name,
          balance = it.balance,
          currency = it.currency.formatCurrency()
        )
      }
    }
  }

  override suspend fun updateAccount(model: AccountModel) {
    val activeAccount = dataStoreManager.getActiveAccount()

    activeAccount?.let {
      Log.i("ACC_update", model.balance)
      Log.i("ACC_update", model.currency)

      val response = httpClient.put(Accounts.Id(id = it.id)) {
        setBody(
          AccountUpdate(
            name = model.name,
            balance = model.balance.formatCashBackwards(),
            currency = model.currency.formatCurrencyBackwards()
          )
        )
        header("Content-Type", "application/json")
      }.body<Account>()

      dataStoreManager.updateActiveAccount(
        AccountInfoModel(
          id = response.id,
          balance = response.balance.formatCash(response.currency),
          currency = response.currency,
          name = response.name
        )
      )
    }
  }
}
