package mega.triple.aaa.data.network.source.impl

import mega.triple.aaa.data.network.api.LocationService
import mega.triple.aaa.data.network.api.ext.safeResultCall
import mega.triple.aaa.data.network.response.CityResponse
import mega.triple.aaa.data.network.response.ContinentResponse
import mega.triple.aaa.data.network.response.CountryResponse
import mega.triple.aaa.data.network.source.LocationNetSource
import javax.inject.Inject

class LocationNetSourceImpl @Inject constructor(
    private val apiService: LocationService
) : LocationNetSource {
    override suspend fun getContinents(language: String): Result<List<ContinentResponse>> =
        safeResultCall {
            apiService.getContinents(language)
        }

    override suspend fun getCountries(
        language: String, continentId: String
    ): Result<List<CountryResponse>> = safeResultCall {
        apiService.getCountries(language, continentId)
    }


    override suspend fun getCities(
        language: String, countryId: String
    ): Result<List<CityResponse>> = safeResultCall {
        apiService.getCities(language, countryId)
    }
}