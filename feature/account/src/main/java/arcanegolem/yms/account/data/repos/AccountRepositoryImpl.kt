package arcanegolem.yms.account.data.repos

import arcanegolem.yms.account.data.remote.api.Accounts
import arcanegolem.yms.account.data.remote.models.Account
import arcanegolem.yms.account.data.remote.models.AccountResponse
import arcanegolem.yms.account.data.remote.models.AccountUpdate
import arcanegolem.yms.account.domain.models.AccountModel
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.core.data.database.dao.QueueDao
import arcanegolem.yms.core.data.database.entities.QueueEntity
import arcanegolem.yms.core.data.database.entities.QueueOperationType
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.datastore.models.AccountInfoModel
import arcanegolem.yms.core.data.utils.currentDateTimeAsInstantString
import arcanegolem.yms.core.data.utils.formatCash
import arcanegolem.yms.core.data.utils.formatCashBackwards
import arcanegolem.yms.core.data.utils.formatCurrency
import arcanegolem.yms.core.data.utils.formatCurrencyBackwards
import arcanegolem.yms.core.utils.NetworkMonitor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.time.ExperimentalTime

/**
 * Реальная имплементация репозитория для работы с счетами
 *
 * @param httpClient Ktor http-клиент
 * @param dataStoreManager хелпер для датастора с данными активного счета
 */
class AccountRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val dataStoreManager : DataStoreManager,
  private val queueDao: QueueDao
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

  override suspend fun refreshActiveAccount() {
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
        updatedAt = first.updatedAt
      )
    )
  }

  override suspend fun getAccount(): Flow<AccountModel?> {
    return dataStoreManager.getActiveAccountFlow().map { infoModel ->
      infoModel?.let {
        AccountModel(
          name = it.name,
          balance = it.balance,
          currency = it.currency.formatCurrency()
        )
      }
    }
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun updateAccount(model: AccountModel) {
    val accountId : Int = dataStoreManager.getActiveAccount()?.id ?: throw RuntimeException("No active account!")

    val payload = AccountUpdate(
      name = model.name,
      balance = model.balance.formatCashBackwards(),
      currency = model.currency.formatCurrencyBackwards()
    )

    if (NetworkMonitor.networkAvailable.value){
      val response = httpClient.put(Accounts.Id(id = accountId)) {
        setBody(payload)
        header("Content-Type", "application/json")
      }.body<Account>()

      dataStoreManager.updateActiveAccount(
        AccountInfoModel(
          id = response.id,
          balance = response.balance.formatCash(response.currency),
          currency = response.currency,
          name = response.name,
          updatedAt = response.updatedAt
        )
      )
    } else {
      queueDao.upsertToQueue(
        QueueEntity(
          identifier = QueueOperationType.ACCOUNT_UPDATE(),
          type = QueueOperationType.ACCOUNT_UPDATE(),
          payload = Json.encodeToString(payload)
        )
      )

      val existingModel = dataStoreManager.getActiveAccount() ?: throw RuntimeException("No active account!")
      dataStoreManager.updateActiveAccount(
        existingModel.copy(
          balance = model.balance.formatCashBackwards(),
          currency = model.currency.formatCurrencyBackwards(),
          name = model.name,
          updatedAt = currentDateTimeAsInstantString()
        )
      )
    }
  }
}
