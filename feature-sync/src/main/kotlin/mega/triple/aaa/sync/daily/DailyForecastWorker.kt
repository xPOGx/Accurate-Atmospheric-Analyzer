package mega.triple.aaa.sync.daily

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import mega.triple.aaa.domain.forecast.daily.UpdateDailyForecastUC
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit

class DailyForecastWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val updateDailyForecastUC: UpdateDailyForecastUC,
) : CoroutineWorker(appContext, workerParams), KoinComponent {
    override suspend fun doWork(): Result {
        if (runAttemptCount > 3) {
            return Result.failure()
        }

        val result = updateDailyForecastUC()

        return if (result.isSuccess) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    companion object {
        const val TAG = "DailyForecastWorker"

        fun createPeriodicWorkRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            return PeriodicWorkRequestBuilder<DailyForecastWorker>(
                1,
                TimeUnit.DAYS,
                1,
                TimeUnit.HOURS,
            )
                .setConstraints(constraints)
                .addTag(TAG)
                .build()
        }
    }
}
