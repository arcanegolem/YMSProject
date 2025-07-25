package arcanegolem.yms.project

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import arcanegolem.yms.account.di.AccountDependencies
import arcanegolem.yms.account.di.AccountDependenciesProvider
import arcanegolem.yms.categories.di.CategoriesDependencies
import arcanegolem.yms.categories.di.CategoriesDependenciesProvider
import arcanegolem.yms.core.utils.NetworkMonitor
import arcanegolem.yms.project.di.ApplicationComponent
import arcanegolem.yms.project.di.DaggerApplicationComponent
import arcanegolem.yms.project.sync.SyncWorker
import arcanegolem.yms.settings.di.SettingsDependencies
import arcanegolem.yms.settings.di.SettingsDependenciesProvider
import arcanegolem.yms.transactions.di.TransactionsDependencies
import arcanegolem.yms.transactions.di.TransactionsDependenciesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class YMSProjectApplication
  : TransactionsDependenciesProvider,
  CategoriesDependenciesProvider,
  AccountDependenciesProvider,
  SettingsDependenciesProvider,
  Configuration.Provider,
  Application() {

  private val applicationCoroutineScope = CoroutineScope(SupervisorJob())

  val applicationComponent : ApplicationComponent by lazy {
    DaggerApplicationComponent.factory()
      .create(this, BuildConfig.TOKEN)
  }

  override fun resolveTransactionsDependencies(): TransactionsDependencies {
    return applicationComponent
  }

  override fun resolveCategoriesDependencies(): CategoriesDependencies {
    return applicationComponent
  }

  override fun resolveAccountDependencies(): AccountDependencies {
    return applicationComponent
  }

  override fun resolveSettingsDependencies(): SettingsDependencies {
    return applicationComponent
  }

  override fun onCreate() {
    super.onCreate()
    scheduleSync()
    setNetworkStateSyncObserver()
  }

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder()
      .setWorkerFactory(applicationComponent.workerFactory())
      .build()

  
  private fun setNetworkStateSyncObserver() {
    applicationCoroutineScope.launch {
      withContext(Dispatchers.IO) {
        NetworkMonitor.networkAvailable.drop(1).collectLatest { networkAvailable ->
          if (networkAvailable) { executeSync() }
        }
      }
    }
  }
  
  private fun executeSync() {
    val workManager = WorkManager.getInstance(this)
    val workRequest = SyncWorker.createExpeditedOneTimeRequest()
    
    workManager.enqueueUniqueWork(
      SyncWorker.ONE_TIME_WORKER_NAME,
      ExistingWorkPolicy.REPLACE,
      workRequest
    )
    
    applicationCoroutineScope.launch {
      workManager.getWorkInfosForUniqueWorkFlow(SyncWorker.ONE_TIME_WORKER_NAME)
        .collect { info ->
          Log.d("SyncWorker_OneTime_STATUS", "State : $info")
        }
    }
  }
  
  private fun scheduleSync() {
    val workManager = WorkManager.getInstance(this)
    val workRequest = SyncWorker.createPeriodicRequest()

    workManager.enqueueUniquePeriodicWork(
      SyncWorker.PERIODIC_WORKER_NAME,
      ExistingPeriodicWorkPolicy.UPDATE,
      workRequest
    )

    applicationCoroutineScope.launch{
      workManager.getWorkInfosForUniqueWorkFlow(SyncWorker.PERIODIC_WORKER_NAME)
        .collect { info ->
          Log.d("SyncWorker_Periodic_STATUS", "State: $info")
        }
    }
  }
}

val Context.applicationComponent : ApplicationComponent
  get() = when(this) {
    is YMSProjectApplication -> applicationComponent
    else -> this.applicationContext.applicationComponent
  }
