package mega.triple.aaa.data.network.source

import mega.triple.aaa.data.network.response.CityResponse
import mega.triple.aaa.data.network.response.ContinentResponse
import mega.triple.aaa.data.network.response.CountryResponse

interface LocationSource {
    suspend fun getContinents(
        language: String,
    ): Result<List<ContinentResponse>>

    suspend fun getCountries(
        language: String,
        continentId: String,
    ): Result<List<CountryResponse>>

    suspend fun getCities(
        language: String,
        countryId: String,
    ): Result<List<CityResponse>>
}