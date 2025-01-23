package mega.triple.aaa.settings.di

import mega.triple.aaa.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureSettingsModule = module {
    viewModelOf(::SettingsViewModel)
}
