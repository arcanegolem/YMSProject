package arcanegolem.yms.account.domain.repos

interface SyncAccountRepository {
  suspend fun syncPendingAccount()
  suspend fun syncExistingAccount()
}