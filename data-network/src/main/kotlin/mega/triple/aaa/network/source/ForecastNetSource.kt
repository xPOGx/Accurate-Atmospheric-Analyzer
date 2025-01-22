package mega.triple.aaa.network.source

import mega.triple.aaa.network.NetworkHelper.DEFAULT_LANGUAGE
import mega.triple.aaa.network.NetworkHelper.DEFAULT_METRIC
import mega.triple.aaa.network.response.daily.DailyForecastWrapper

interface ForecastNetSource {
    suspend fun get5dayForecast(
        language: String = DEFAULT_LANGUAGE,
        isMetric: Boolean = DEFAULT_METRIC,
        locationKey: String,
    ): Result<DailyForecastWrapper>
}