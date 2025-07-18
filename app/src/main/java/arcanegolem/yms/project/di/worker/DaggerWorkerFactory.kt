package arcanegolem.yms.project.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class DaggerWorkerFactory @Inject constructor(
  private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<WorkerAssistedFactory<out ListenableWorker>>>
) : WorkerFactory() {
  override fun createWorker(
    appContext: Context,
    workerClassName: String,
    workerParameters: WorkerParameters
  ): ListenableWorker? {
    val workerClass = Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)

    val factoryProvider = workerFactories[workerClass] ?: throw IllegalArgumentException("No factory for $workerClass")

    return factoryProvider.get().create(appContext, workerParameters)
  }
}