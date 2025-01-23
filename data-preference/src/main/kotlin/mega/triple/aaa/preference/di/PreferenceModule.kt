package mega.triple.aaa.preference.di

import mega.triple.aaa.preference.SettingsDatastore
import mega.triple.aaa.preference.impl.SettingsDatastoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataPreferenceModule = module {
    single<SettingsDatastore> { SettingsDatastoreImpl(androidContext()) }
}
