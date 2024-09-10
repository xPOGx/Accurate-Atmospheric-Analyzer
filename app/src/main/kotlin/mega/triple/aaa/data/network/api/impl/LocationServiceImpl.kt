package mega.triple.aaa.data.network.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import mega.triple.aaa.BuildConfig
import mega.triple.aaa.data.network.api.LocationService
import javax.inject.Inject

class LocationServiceImpl @Inject constructor(
    private val client: HttpClient
) : LocationService {
    override suspend fun getContinents(language: String): HttpResponse {
        return client.get(continentUrl()) {
            parameter("apikey", BuildConfig.API_KEY)
            parameter("language", language)
        }
    }

    override suspend fun getCountries(language: String, continentId: String): HttpResponse {
        return client.get(countyUrl(continentId)) {
            parameter("apikey", BuildConfig.API_KEY)
            parameter("language", language)
        }
    }

    override suspend fun getCities(language: String, countryId: String): HttpResponse {
        return client.get(cityUrl(countryId)) {
            parameter("apikey", BuildConfig.API_KEY)
            parameter("language", language)
        }
    }

    companion object {
        private const val BASE_URL = "https://dataservice.accuweather.com"
        private const val LOCATION_URL = "$BASE_URL/locations/v1"

        private fun continentUrl() = "$LOCATION_URL/regions"
        private fun countyUrl(continentId: String) = "$LOCATION_URL/countries/$continentId"
        private fun cityUrl(countryId: String) = "$LOCATION_URL/adminareas/$countryId"
    }
}
