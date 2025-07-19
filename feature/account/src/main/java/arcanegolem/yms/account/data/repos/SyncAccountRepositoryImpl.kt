package arcanegolem.yms.account.data.repos

import arcanegolem.yms.account.data.remote.api.Accounts
import arcanegolem.yms.account.data.remote.models.Account
import arcanegolem.yms.account.data.remote.models.AccountUpdate
import arcanegolem.yms.account.domain.repos.SyncAccountRepository
import arcanegolem.yms.core.data.database.dao.QueueDao
import arcanegolem.yms.core.data.database.entities.QueueOperationType
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.datastore.models.AccountInfoModel
import arcanegolem.yms.core.data.utils.formatCash
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import javax.inject.Inject

/**
 * Реализация репозитория синхронизации счета, честно говоря тут какой-то мудреной стратегии
 * сихронизации нет, попросту отправляются изменения которые были произведены в оффлайн-режиме
 * и поттягиваются изменения с сервера, в приоритет ставятся данные на клиенте при изменении счета
 */
class SyncAccountRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val dataStoreManager: DataStoreManager,
  private val queueDao: QueueDao
) : SyncAccountRepository {
  override suspend fun syncPendingAccount() {
    val queue = queueDao.getQueue()

    val accountId = dataStoreManager.getActiveAccount()?.id
      ?: throw RuntimeException("No active account!")

    queue.forEach { entity ->
      val type = QueueOperationType.fromString(entity.type)

      when (type) {
        QueueOperationType.ACCOUNT_UPDATE -> {
          val payload = entity.payloadAsObject<AccountUpdate>()
          val updateResponse = httpClient.put(Accounts.Id(id = accountId)) {
            setBody(payload)
            header("Content-Type", "application/json")
          }

          if (updateResponse.status.value == 200) {
            queueDao.deleteFromQueue(entity)
            val body = updateResponse.body<Account>()
            dataStoreManager.updateActiveAccount(
              AccountInfoModel(
                id = body.id,
                balance = body.balance.formatCash(body.currency),
                currency = body.currency,
                name = body.name,
                updatedAt = body.updatedAt
              )
            )
          }
        }
        else -> {}
      }
    }
  }

  override suspend fun syncExistingAccount() {
    val remoteAccount = httpClient.get(Accounts()).body<List<Account>>().first()

    dataStoreManager.updateActiveAccount(
      AccountInfoModel(
        id = remoteAccount.id,
        balance = remoteAccount.balance,
        currency = remoteAccount.currency,
        name = remoteAccount.name,
        updatedAt = remoteAccount.updatedAt
      )
    )
  }
}