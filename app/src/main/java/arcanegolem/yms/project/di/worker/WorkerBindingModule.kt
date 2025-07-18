package arcanegolem.yms.project.di.worker

import androidx.work.ListenableWorker
import arcanegolem.yms.project.sync.SyncWorker
import arcanegolem.yms.project.sync.SyncWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
  @Binds
  @IntoMap
  @WorkerKey(SyncWorker::class)
  fun bindSyncWorkerFactoryToWorkerAssistedFactory(factory: SyncWorkerFactory) :
      WorkerAssistedFactory<out ListenableWorker>
}