package mega.triple.aaa.data.network.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import mega.triple.aaa.BuildConfig
import mega.triple.aaa.data.network.api.LocationService
import mega.triple.aaa.data.network.NetworkHelper.LOCATION_URL
import mega.triple.aaa.data.network.NetworkHelper.PARAM_API_KEY
import mega.triple.aaa.data.network.NetworkHelper.PARAM_LANGUAGE
import mega.triple.aaa.data.network.NetworkHelper.PARAM_QUERY
import javax.inject.Inject

class LocationServiceImpl @Inject constructor(
    private val client: HttpClient
) : LocationService {
    override suspend fun getContinents(language: String): HttpResponse {
        return client.get(continentUrl()) {
            parameter(PARAM_API_KEY, BuildConfig.API_KEY)
            parameter(PARAM_LANGUAGE, language)
        }
    }

    override suspend fun getCountries(language: String, continentId: String): HttpResponse {
        return client.get(countyUrl(continentId)) {
            parameter(PARAM_API_KEY, BuildConfig.API_KEY)
            parameter(PARAM_LANGUAGE, language)
        }
    }

    override suspend fun getCities(language: String, countryId: String): HttpResponse {
        return client.get(cityUrl(countryId)) {
            parameter(PARAM_API_KEY, BuildConfig.API_KEY)
            parameter(PARAM_LANGUAGE, language)
        }
    }

    override suspend fun getCityKey(
        countryId: String,
        cityId: String,
        cityName: String,
    ): HttpResponse {
        return client.get(cityKeyUrl(countryId, cityId)) {
            parameter(PARAM_API_KEY, BuildConfig.API_KEY)
            parameter(PARAM_QUERY, cityName)
        }
    }

    companion object {
        private fun continentUrl() = "$LOCATION_URL/regions"
        private fun countyUrl(continentId: String) = "$LOCATION_URL/countries/$continentId"
        private fun cityUrl(countryId: String) = "$LOCATION_URL/adminareas/$countryId"
        private fun cityKeyUrl(countryId: String, cityId: String) =
            "$LOCATION_URL/cities/$countryId/$cityId/search"
    }
}
