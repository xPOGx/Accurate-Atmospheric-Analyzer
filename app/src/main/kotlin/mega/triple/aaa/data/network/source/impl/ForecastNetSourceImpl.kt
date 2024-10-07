package mega.triple.aaa.data.network.source.impl

import mega.triple.aaa.data.network.api.ForecastService
import mega.triple.aaa.data.network.api.ext.safeResultCall
import mega.triple.aaa.data.network.response.daily.DailyForecastWrapper
import mega.triple.aaa.data.network.source.ForecastNetSource
import javax.inject.Inject

class ForecastNetSourceImpl @Inject constructor(
    private val apiService: ForecastService
) : ForecastNetSource {
    override suspend fun get5dayForecast(
        language: String,
        isMetric: Boolean,
        locationKey: String,
    ): Result<List<DailyForecastWrapper>> = safeResultCall {
        apiService.get5dayForecast(language, locationKey, isMetric)
    }
}