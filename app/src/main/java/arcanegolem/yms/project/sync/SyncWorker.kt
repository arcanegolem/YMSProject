package arcanegolem.yms.project.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import arcanegolem.yms.account.domain.repos.SyncAccountRepository
import arcanegolem.yms.categories.domain.repos.SyncCategoriesRepository
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.core.data.utils.currentDateTimeAsString
import arcanegolem.yms.transactions.domain.repos.SyncTransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class SyncWorker(
  context : Context,
  params : WorkerParameters,
  private val syncTransactionsRepository: SyncTransactionsRepository,
  private val syncAccountRepository: SyncAccountRepository,
  private val syncCategoriesRepository: SyncCategoriesRepository,
  private val dataStoreManager: DataStoreManager
) : CoroutineWorker(context, params) {
  override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
    try {
      syncTransactionsRepository.syncPendingTransactions()
      syncTransactionsRepository.syncExistingTransactions()
      syncAccountRepository.syncPendingAccount()
      syncAccountRepository.syncExistingAccount()
      syncCategoriesRepository.syncCategories()

      dataStoreManager.updateLastSync(currentDateTimeAsString())
      Result.success()
    } catch (e : Exception) {
      Result.failure()
    }
  }

  companion object {
    const val WORKER_NAME = "global_sync_yms_proj"

    fun createRequest() : PeriodicWorkRequest {
      return PeriodicWorkRequestBuilder<SyncWorker>(3, TimeUnit.HOURS)
        .setConstraints(
          Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        ).build()
    }
  }
}