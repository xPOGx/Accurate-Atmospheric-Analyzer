package mega.triple.aaa.local.di

import mega.triple.aaa.local.AppDb
import mega.triple.aaa.local.dao.forecast.DailyForecastDao
import mega.triple.aaa.local.dao.location.CityDao
import mega.triple.aaa.local.dao.location.ContinentDao
import mega.triple.aaa.local.dao.location.CountryDao
import mega.triple.aaa.local.source.ForecastDbSource
import mega.triple.aaa.local.source.LocationDbSource
import mega.triple.aaa.local.source.impl.ForecastDbSourceImpl
import mega.triple.aaa.local.source.impl.LocationDbSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataLocalModule = module {
    single { AppDb.getDatabase(androidContext()) }

    single<ContinentDao> { get<AppDb>().continentDao() }
    single<CountryDao> { get<AppDb>().countryDao() }
    single<CityDao> { get<AppDb>().cityDao() }
    single<DailyForecastDao> { get<AppDb>().dailyForecastDao() }

    singleOf(::LocationDbSourceImpl) bind LocationDbSource::class
    singleOf(::ForecastDbSourceImpl) bind ForecastDbSource::class
}
