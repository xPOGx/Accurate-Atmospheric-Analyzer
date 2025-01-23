package mega.triple.aaa.sync.di

import mega.triple.aaa.sync.daily.DailyForecastWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val featureSyncModule = module {
    worker {
        DailyForecastWorker(androidContext(), get(), get())
    }
}
