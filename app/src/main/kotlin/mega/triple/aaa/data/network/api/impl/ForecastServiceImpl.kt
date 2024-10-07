package mega.triple.aaa.data.network.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import mega.triple.aaa.BuildConfig
import mega.triple.aaa.data.network.api.ForecastService
import mega.triple.aaa.data.network.NetworkHelper.FORECAST_URL
import mega.triple.aaa.data.network.NetworkHelper.PARAM_API_KEY
import mega.triple.aaa.data.network.NetworkHelper.PARAM_DETAILS
import mega.triple.aaa.data.network.NetworkHelper.PARAM_LANGUAGE
import mega.triple.aaa.data.network.NetworkHelper.PARAM_METRIC
import javax.inject.Inject

class ForecastServiceImpl @Inject constructor(
    private val client: HttpClient
) : ForecastService {
    override suspend fun get5dayForecast(
        language: String,
        locationKey: String,
        isMetric: Boolean
    ): HttpResponse {
        return client.get(daily5(locationKey)) {
            parameter(PARAM_API_KEY, BuildConfig.API_KEY)
            parameter(PARAM_LANGUAGE, language)
            parameter(PARAM_DETAILS, true)
            parameter(PARAM_METRIC, isMetric)
        }
    }

    companion object {
        private fun daily5(locationKey: String) = "$FORECAST_URL/daily/5day/$locationKey"
    }
}
