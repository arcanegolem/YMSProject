package arcanegolem.yms.transactions.domain.repos

interface SyncTransactionsRepository {
  suspend fun syncPendingTransactions()
  suspend fun syncExistingTransactions()
}