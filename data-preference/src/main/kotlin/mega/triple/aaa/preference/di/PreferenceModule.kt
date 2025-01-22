package mega.triple.aaa.preference.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mega.triple.aaa.preference.SettingsDatastore
import mega.triple.aaa.preference.impl.SettingsDatastoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun provideSettingsDatastore(
        @ApplicationContext context: Context,
    ): SettingsDatastore = SettingsDatastoreImpl(context)
}
