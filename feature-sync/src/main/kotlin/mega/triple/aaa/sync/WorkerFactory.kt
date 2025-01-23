package mega.triple.aaa.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

class WorkerFactory(private val koin: Koin) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return koin.get { parametersOf(appContext, workerParameters) }
    }
}
