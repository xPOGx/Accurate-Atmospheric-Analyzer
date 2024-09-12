package mega.triple.aaa.data.network.source

import mega.triple.aaa.data.network.response.CityResponse
import mega.triple.aaa.data.network.response.ContinentResponse
import mega.triple.aaa.data.network.response.CountryResponse

interface LocationNetSource {
    suspend fun getContinents(
        language: String = "uk-ua",
    ): Result<List<ContinentResponse>>

    suspend fun getCountries(
        language: String = "uk-ua",
        continentId: String,
    ): Result<List<CountryResponse>>

    suspend fun getCities(
        language: String = "uk-ua",
        countryId: String,
    ): Result<List<CityResponse>>
}