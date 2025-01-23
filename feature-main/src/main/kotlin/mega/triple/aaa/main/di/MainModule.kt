package mega.triple.aaa.main.di

import mega.triple.aaa.main.AAAViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureMainModule = module {
    viewModelOf(::AAAViewModel)
}
