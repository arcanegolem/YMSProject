package arcanegolem.yms.project.sync

import android.content.Context
import androidx.work.WorkerParameters
import arcanegolem.yms.account.domain.repos.SyncAccountRepository
import arcanegolem.yms.categories.domain.repos.SyncCategoriesRepository
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.project.di.worker.WorkerAssistedFactory
import arcanegolem.yms.transactions.domain.repos.SyncTransactionsRepository
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor(
  private val syncTransactionsRepository: SyncTransactionsRepository,
  private val syncAccountRepository: SyncAccountRepository,
  private val syncCategoriesRepository: SyncCategoriesRepository,
  private val dataStoreManager: DataStoreManager
) : WorkerAssistedFactory<SyncWorker> {
  override fun create(
    context: Context,
    params: WorkerParameters
  ): SyncWorker {
    return SyncWorker(
      context,
      params,
      syncTransactionsRepository,
      syncAccountRepository,
      syncCategoriesRepository,
      dataStoreManager
    )
  }
}