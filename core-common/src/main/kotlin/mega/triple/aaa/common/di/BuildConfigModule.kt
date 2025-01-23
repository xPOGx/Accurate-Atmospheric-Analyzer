package mega.triple.aaa.common.di

import mega.triple.aaa.common.BuildConfigModelProvider
import mega.triple.aaa.common.impl.BuildConfigModelProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreCommonModule = module {
    singleOf(::BuildConfigModelProviderImpl) bind BuildConfigModelProvider::class
}
