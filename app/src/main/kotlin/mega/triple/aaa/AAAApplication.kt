package mega.triple.aaa

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp
import mega.triple.aaa.common.BuildConfigModelProvider
import mega.triple.aaa.common.analytic.AAAAnalytic
import mega.triple.aaa.sync.DailyForecastWorker
import javax.inject.Inject

@HiltAndroidApp
class AAAApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    @Inject
    lateinit var buildConfigProvider: BuildConfigModelProvider

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()

        // Firebase
        Firebase.initialize(this)
        AAAAnalytic.subscribe {
            Firebase.analytics.logEvent(it, null)
        }

        with(WorkManager.getInstance(this)) {
            enqueueUniquePeriodicWork(
                DailyForecastWorker.TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                DailyForecastWorker.createPeriodicWorkRequest(),
            )
        }

        buildConfigProvider.populate(
            isDebug = BuildConfig.DEBUG,
            apiKey = BuildConfig.API_KEY
        )
    }
}
