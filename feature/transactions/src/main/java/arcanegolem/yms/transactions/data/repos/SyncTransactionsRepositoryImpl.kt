package arcanegolem.yms.transactions.data.repos

import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.dao.QueueDao
import arcanegolem.yms.core.data.database.dao.TransactionsDao
import arcanegolem.yms.core.data.database.entities.QueueOperationType
import arcanegolem.yms.core.data.database.entities.TransactionEntity
import arcanegolem.yms.core.data.utils.parseMillis
import arcanegolem.yms.transactions.data.remote.api.Transactions
import arcanegolem.yms.transactions.data.remote.models.TransactionCreate
import arcanegolem.yms.transactions.data.remote.models.TransactionCreateResponse
import arcanegolem.yms.transactions.data.remote.models.TransactionDelete
import arcanegolem.yms.transactions.data.remote.models.TransactionResponse
import arcanegolem.yms.transactions.data.remote.models.TransactionUpdate
import arcanegolem.yms.transactions.domain.repos.SyncTransactionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Реализация репозитория синхронизации транзакций, честно говоря тут какой-то мудреной стратегии
 * сихронизации нет, попросту отправляются изменения которые были произведены в оффлайн-режиме
 * и поттягиваются изменения с сервера, в приоритет ставятся данные на клиенте при изменении транзакций
 */
class SyncTransactionsRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val transactionsDao: TransactionsDao,
  private val queueDao: QueueDao,
  private val categoryDao: CategoryDao
) : SyncTransactionsRepository {
  override suspend fun syncPendingTransactions() {
    val queue = queueDao.getQueue()

    queue.forEach { entity ->
      val type = QueueOperationType.fromString(entity.type)

      when (type) {
        QueueOperationType.TRANSACTION_UPDATE -> {
          val payload = entity.payloadAsObject<TransactionUpdate>()
          val updateResponse = httpClient.put(Transactions.Id(id = payload.transactionId)) {
            setBody(payload)
            header("Content-Type", "application/json")
          }
          if (updateResponse.status.value == 200){
            queueDao.deleteFromQueue(entity)

            val body = updateResponse.body<TransactionResponse>()
            transactionsDao.upsertTransaction(
              TransactionEntity(
                transactionId = body.id,
                categoryId = body.category.id,
                amount = body.amount,
                transactionDateMillis = body.transactionDate.parseMillis(),
                createdAt = body.createdAt,
                updatedAt = body.updatedAt,
                comment = body.comment ?: "",
                isIncome = body.category.isIncome
              )
            )
          }
        }
        QueueOperationType.TRANSACTION_CREATE -> {
          val payload = entity.payloadAsObject<TransactionCreate>()
          val createResponse = httpClient.post(Transactions()) {
            setBody(payload)
            header("Content-Type", "application/json")
          }
          if (createResponse.status.value == 200) {
            queueDao.deleteFromQueue(entity)
            val arbitraryEntity = transactionsDao.getByArbitraryTransactionId(entity.arbitraryId)
            arbitraryEntity?.let { transactionsDao.deleteArbitraryTransaction(arbitraryEntity) }
            val body = createResponse.body<TransactionCreateResponse>()
            transactionsDao.upsertTransaction(
              TransactionEntity(
                transactionId = body.id,
                categoryId = body.categoryId,
                amount = body.amount,
                transactionDateMillis = body.transactionDate.parseMillis(),
                createdAt = body.createdAt,
                updatedAt = body.updatedAt,
                comment = body.comment ?: "",
                isIncome = categoryDao.getCategoryById(body.categoryId)?.isIncome ?: false
              )
            )
          }
        }
        QueueOperationType.TRANSACTION_DELETE -> {
          val payload = entity.payloadAsObject<TransactionDelete>()
          httpClient.delete(Transactions.Id(id = payload.transactionId))

          queueDao.deleteFromQueue(entity)
        }
        else -> {}
      }
    }
  }

  @OptIn(ExperimentalTime::class)
  override suspend fun syncExistingTransactions() {
    // Берем все транзакции на устройстве
    val onDeviceTransactions = transactionsDao.getAllTransactions()

    onDeviceTransactions.forEach { localTransaction ->
      val remoteTransactionGetResponse = httpClient
        .get(Transactions.Id(id = localTransaction.transactionId))

      val remoteGetHttpStatusCode = remoteTransactionGetResponse.status

      // Смотрим если есть транзакция на сервере
      if (remoteGetHttpStatusCode.value == 200){
        val remoteTransaction = remoteTransactionGetResponse.body<TransactionResponse>()

        val remoteUpdated = Instant.parse(remoteTransaction.updatedAt)
        val localUpdated = Instant.parse(localTransaction.updatedAt)

        // Если на сервере более новая берем оттуда
        if (remoteUpdated > localUpdated) {
          transactionsDao.upsertTransaction(
            localTransaction.copy(
              categoryId = remoteTransaction.category.id,
              amount = remoteTransaction.amount,
              transactionDateMillis = remoteTransaction.transactionDate.parseMillis(),
              comment = remoteTransaction.comment ?: "",
              isIncome = remoteTransaction.category.isIncome
            )
          )
          // Если локальная более новая то обновляем
        }
        // Если транзакции на сервере нет, то удаляем локально, потому что добавление и удаление транзакций
        // которые были созданы локально произойдет раньше
      } else if (remoteGetHttpStatusCode.value == 404) {
        transactionsDao.deleteByTransactionId(localTransaction.transactionId)
      }
    }
  }
}