package mega.triple.aaa.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mega.triple.aaa.common.BuildConfigModelProvider
import mega.triple.aaa.common.impl.BuildConfigModelProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildConfigModule {

    @Provides
    @Singleton
    fun provideBuildConfigModelProvider(): BuildConfigModelProvider = BuildConfigModelProviderImpl()
}
