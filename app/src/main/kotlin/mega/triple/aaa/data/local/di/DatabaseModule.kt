package mega.triple.aaa.data.local.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mega.triple.aaa.data.local.AppDb
import mega.triple.aaa.data.local.dao.location.CityDao
import mega.triple.aaa.data.local.dao.location.ContinentDao
import mega.triple.aaa.data.local.dao.location.CountryDao
import mega.triple.aaa.data.local.source.LocationDbSource
import mega.triple.aaa.data.local.source.impl.LocationDbSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDb = AppDb.getDatabase(context)

    @Provides
    fun provideContinentDao(appDb: AppDb): ContinentDao = appDb.continentDao()

    @Provides
    fun provideCountryDao(appDb: AppDb): CountryDao = appDb.countryDao()

    @Provides
    fun provideCityDao(appDb: AppDb): CityDao = appDb.cityDao()

    @Provides
    fun provideLocationDbSource(
        continentDao: ContinentDao,
        countryDao: CountryDao,
        cityDao: CityDao,
    ): LocationDbSource = LocationDbSourceImpl(continentDao, countryDao, cityDao)
}