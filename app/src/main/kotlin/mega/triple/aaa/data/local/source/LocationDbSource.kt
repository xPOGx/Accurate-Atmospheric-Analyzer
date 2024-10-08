package mega.triple.aaa.data.local.source

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.model.location.CityDbModel
import mega.triple.aaa.data.local.model.location.ContinentDbModel
import mega.triple.aaa.data.local.model.location.CountryDbModel

interface LocationDbSource {
    fun getContinents(): Flow<List<ContinentDbModel>>
    suspend fun insertContinents(list: List<ContinentDbModel>)

    fun getCountries(continentId: String): Flow<List<CountryDbModel>>
    suspend fun insertCountries(list: List<CountryDbModel>)

    fun getCities(countryId: String): Flow<List<CityDbModel>>
    suspend fun insertCities(list: List<CityDbModel>)
}