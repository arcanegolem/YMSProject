package arcanegolem.yms.project.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface WorkerAssistedFactory<T : ListenableWorker> {
  fun create(context : Context, params: WorkerParameters) : T
}