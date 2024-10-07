package mega.triple.aaa.data.network.api

import io.ktor.client.statement.HttpResponse

interface LocationService {
    suspend fun getContinents(
        language: String,
    ): HttpResponse

    suspend fun getCountries(
        language: String,
        continentId: String,
    ): HttpResponse

    suspend fun getCities(
        language: String,
        countryId: String,
    ): HttpResponse

    suspend fun getCityKey(
        countryId: String,
        cityId: String,
        cityName: String,
    ): HttpResponse
}
