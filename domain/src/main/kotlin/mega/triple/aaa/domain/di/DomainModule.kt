package mega.triple.aaa.domain.di

import mega.triple.aaa.domain.ext.ForecastHelper
import mega.triple.aaa.domain.forecast.daily.Get5DayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTodayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTomorrowForecastUC
import mega.triple.aaa.domain.forecast.daily.GetYesterdayForecastUC
import mega.triple.aaa.domain.forecast.daily.UpdateDailyForecastUC
import mega.triple.aaa.domain.forecast.daily.impl.Get5DayForecastUCImpl
import mega.triple.aaa.domain.forecast.daily.impl.GetTodayForecastUCImpl
import mega.triple.aaa.domain.forecast.daily.impl.GetTomorrowForecastUCImpl
import mega.triple.aaa.domain.forecast.daily.impl.GetYesterdayForecastUCImpl
import mega.triple.aaa.domain.forecast.daily.impl.UpdateDailyForecastUCImpl
import mega.triple.aaa.domain.location.GetCitiesUC
import mega.triple.aaa.domain.location.GetCityKeyUC
import mega.triple.aaa.domain.location.GetContinentsUC
import mega.triple.aaa.domain.location.GetCountriesUC
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.SetLocationUC
import mega.triple.aaa.domain.location.impl.GetCitiesUCImpl
import mega.triple.aaa.domain.location.impl.GetCityKeyUCImpl
import mega.triple.aaa.domain.location.impl.GetContinentsUCImpl
import mega.triple.aaa.domain.location.impl.GetCountriesUCImpl
import mega.triple.aaa.domain.location.impl.GetLocationUCImpl
import mega.triple.aaa.domain.location.impl.SetLocationUCImpl
import mega.triple.aaa.domain.settings.GetLastUpdateUC
import mega.triple.aaa.domain.settings.GetThemeUC
import mega.triple.aaa.domain.settings.SetLastUpdateUC
import mega.triple.aaa.domain.settings.SetThemeUC
import mega.triple.aaa.domain.settings.impl.GetLastUpdateUCImpl
import mega.triple.aaa.domain.settings.impl.GetThemeUCImpl
import mega.triple.aaa.domain.settings.impl.SetLastUpdateUCImpl
import mega.triple.aaa.domain.settings.impl.SetThemeUCImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    single { ForecastHelper(get(), get(), get(), get(), get()) }
    // Location
    singleOf(::GetCitiesUCImpl) bind GetCitiesUC::class
    singleOf(::GetCityKeyUCImpl) bind GetCityKeyUC::class
    singleOf(::GetContinentsUCImpl) bind GetContinentsUC::class
    singleOf(::GetCountriesUCImpl) bind GetCountriesUC::class
    singleOf(::GetLocationUCImpl) bind GetLocationUC::class
    singleOf(::SetLocationUCImpl) bind SetLocationUC::class
    // Daily forecast
    singleOf(::Get5DayForecastUCImpl) bind Get5DayForecastUC::class
    singleOf(::GetTodayForecastUCImpl) bind GetTodayForecastUC::class
    singleOf(::GetTomorrowForecastUCImpl) bind GetTomorrowForecastUC::class
    singleOf(::GetYesterdayForecastUCImpl) bind GetYesterdayForecastUC::class
    singleOf(::UpdateDailyForecastUCImpl) bind UpdateDailyForecastUC::class
    // Theme
    singleOf(::GetThemeUCImpl) bind GetThemeUC::class
    singleOf(::SetThemeUCImpl) bind SetThemeUC::class
    // Last Update Date
    singleOf(::GetLastUpdateUCImpl) bind GetLastUpdateUC::class
    singleOf(::SetLastUpdateUCImpl) bind SetLastUpdateUC::class
}
