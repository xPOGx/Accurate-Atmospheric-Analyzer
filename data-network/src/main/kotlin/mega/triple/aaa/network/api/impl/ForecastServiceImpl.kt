package mega.triple.aaa.network.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import mega.triple.aaa.common.BuildConfigModelProvider
import mega.triple.aaa.network.NetworkHelper.FORECAST_URL
import mega.triple.aaa.network.NetworkHelper.PARAM_API_KEY
import mega.triple.aaa.network.NetworkHelper.PARAM_DETAILS
import mega.triple.aaa.network.NetworkHelper.PARAM_LANGUAGE
import mega.triple.aaa.network.NetworkHelper.PARAM_METRIC
import mega.triple.aaa.network.api.ForecastService

class ForecastServiceImpl(
    private val client: HttpClient,
    buildConfigModelProvider: BuildConfigModelProvider,
) : ForecastService {
    private val buildConfig = buildConfigModelProvider.buildConfig

    override suspend fun get5dayForecast(
        language: String,
        locationKey: String,
        isMetric: Boolean
    ): HttpResponse {
        return client.get(daily5(locationKey)) {
            parameter(PARAM_API_KEY, buildConfig.apiKey)
            parameter(PARAM_LANGUAGE, language)
            parameter(PARAM_DETAILS, true)
            parameter(PARAM_METRIC, isMetric)
        }
    }

    companion object {
        private fun daily5(locationKey: String) = "$FORECAST_URL/daily/5day/$locationKey"
    }
}
