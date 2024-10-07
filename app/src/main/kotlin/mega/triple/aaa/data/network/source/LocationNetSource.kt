package mega.triple.aaa.data.network.source

import mega.triple.aaa.data.network.NetworkHelper.DEFAULT_LANGUAGE
import mega.triple.aaa.data.network.response.location.CityKeyResponse
import mega.triple.aaa.data.network.response.location.CityResponse
import mega.triple.aaa.data.network.response.location.ContinentResponse
import mega.triple.aaa.data.network.response.location.CountryResponse

interface LocationNetSource {
    suspend fun getContinents(
        language: String = DEFAULT_LANGUAGE,
    ): Result<List<ContinentResponse>>

    suspend fun getCountries(
        language: String = DEFAULT_LANGUAGE,
        continentId: String,
    ): Result<List<CountryResponse>>

    suspend fun getCities(
        language: String = DEFAULT_LANGUAGE,
        countryId: String,
    ): Result<List<CityResponse>>

    suspend fun getCityKey(
        countryId: String,
        cityId: String,
        cityName: String
    ): Result<List<CityKeyResponse>>
}