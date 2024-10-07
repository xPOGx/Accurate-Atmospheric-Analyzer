package mega.triple.aaa.data.network.api

import io.ktor.client.statement.HttpResponse

interface ForecastService {
    suspend fun get5dayForecast(
        language: String,
        locationKey: String,
        isMetric: Boolean,
    ): HttpResponse
}
