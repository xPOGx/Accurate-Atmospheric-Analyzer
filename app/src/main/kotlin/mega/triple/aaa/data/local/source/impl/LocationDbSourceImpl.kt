package mega.triple.aaa.data.local.source.impl

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.dao.location.CityDao
import mega.triple.aaa.data.local.dao.location.ContinentDao
import mega.triple.aaa.data.local.dao.location.CountryDao
import mega.triple.aaa.data.local.model.location.CityDbModel
import mega.triple.aaa.data.local.model.location.ContinentDbModel
import mega.triple.aaa.data.local.model.location.CountryDbModel
import mega.triple.aaa.data.local.source.LocationDbSource
import javax.inject.Inject

class LocationDbSourceImpl @Inject constructor(
    private val continentDao: ContinentDao,
    private val countryDao: CountryDao,
    private val cityDao: CityDao,
) : LocationDbSource {
    override fun getContinents(): Flow<List<ContinentDbModel>> = continentDao.getContinents()

    override suspend fun insertContinents(list: List<ContinentDbModel>) =
        continentDao.insertContinents(list)

    override fun getCountries(continentId: String): Flow<List<CountryDbModel>> =
        countryDao.getCountries(continentId)

    override suspend fun insertCountries(list: List<CountryDbModel>) =
        countryDao.insertCountries(list)

    override fun getCities(countryId: String): Flow<List<CityDbModel>> =
        cityDao.getCities(countryId)

    override suspend fun insertCities(list: List<CityDbModel>) = cityDao.insertCities(list)
}