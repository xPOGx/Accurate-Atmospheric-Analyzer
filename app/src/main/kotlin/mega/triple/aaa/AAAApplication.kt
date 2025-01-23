package mega.triple.aaa

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.initialize
import mega.triple.aaa.common.BuildConfigModelProvider
import mega.triple.aaa.common.analytic.AAAAnalytic
import mega.triple.aaa.common.di.coreCommonModule
import mega.triple.aaa.domain.di.domainModule
import mega.triple.aaa.home.di.featureHomeModule
import mega.triple.aaa.local.di.dataLocalModule
import mega.triple.aaa.main.di.featureMainModule
import mega.triple.aaa.network.di.dataNetworkModule
import mega.triple.aaa.preference.di.dataPreferenceModule
import mega.triple.aaa.proto.di.dataProtoModule
import mega.triple.aaa.search.di.featureSearchModule
import mega.triple.aaa.settings.di.featureSettingsModule
import mega.triple.aaa.sync.WorkerFactory
import mega.triple.aaa.sync.daily.DailyForecastWorker
import mega.triple.aaa.sync.di.featureSyncModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AAAApplication : Application(), Configuration.Provider {
    private val buildConfigProvider: BuildConfigModelProvider by inject()

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(WorkerFactory(getKoin())).build()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AAAApplication)
            modules(
                coreCommonModule,
                dataLocalModule,
                dataNetworkModule,
                dataPreferenceModule,
                dataProtoModule,
                domainModule,
                featureMainModule,
                featureSyncModule,
                featureSearchModule,
                featureSettingsModule,
                featureHomeModule,
            )
        }

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
