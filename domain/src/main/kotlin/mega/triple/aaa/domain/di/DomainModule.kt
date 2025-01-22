package mega.triple.aaa.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
import mega.triple.aaa.domain.theme.GetThemeUC
import mega.triple.aaa.domain.theme.SetThemeUC
import mega.triple.aaa.domain.theme.impl.GetThemeUCImpl
import mega.triple.aaa.domain.theme.impl.SetThemeUCImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    // Location
    @Binds
    abstract fun bindGetCitiesUC(useCase: GetCitiesUCImpl): GetCitiesUC

    @Binds
    abstract fun bindGetCityKeyUC(useCase: GetCityKeyUCImpl): GetCityKeyUC

    @Binds
    abstract fun bindGetContinentsUC(useCase: GetContinentsUCImpl): GetContinentsUC

    @Binds
    abstract fun bindGetCountriesUC(useCase: GetCountriesUCImpl): GetCountriesUC

    @Binds
    abstract fun bindGetLocationUC(useCase: GetLocationUCImpl): GetLocationUC

    @Binds
    abstract fun bindSetLocationUC(useCase: SetLocationUCImpl): SetLocationUC

    // Daily forecast
    @Binds
    abstract fun bindGet5DayForecastUC(useCase: Get5DayForecastUCImpl): Get5DayForecastUC

    @Binds
    abstract fun bindGetTodayForecastUC(useCase: GetTodayForecastUCImpl): GetTodayForecastUC

    @Binds
    abstract fun bindGetTomorrowForecastUC(useCase: GetTomorrowForecastUCImpl): GetTomorrowForecastUC

    @Binds
    abstract fun bindGetYesterdayForecastUC(useCase: GetYesterdayForecastUCImpl): GetYesterdayForecastUC

    @Binds
    abstract fun bindUpdateDailyForecastUC(useCase: UpdateDailyForecastUCImpl): UpdateDailyForecastUC

    // Theme
    @Binds
    abstract fun bindGetThemeUC(useCase: GetThemeUCImpl): GetThemeUC

    @Binds
    abstract fun bindSetThemeUC(useCase: SetThemeUCImpl): SetThemeUC
}