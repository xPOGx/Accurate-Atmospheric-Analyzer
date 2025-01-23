package mega.triple.aaa.domain.location.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.EmptyDatabase
import mega.triple.aaa.domain.location.GetCitiesUC
import mega.triple.aaa.domain.location.model.CityDomainModel
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.LocationDbSource
import mega.triple.aaa.network.source.LocationNetSource

class GetCitiesUCImpl(
    private val locationDbSource: LocationDbSource,
    private val locationNetSource: LocationNetSource,
) : GetCitiesUC {
    override suspend operator fun invoke(
        continentId: String,
        countryId: String,
    ): Result<List<CityDomainModel>> {
        return try {
            val dbModels = locationDbSource.getCities(countryId).first()
            if (dbModels.isEmpty()) {
                throw EmptyDatabase()
            } else {
                val domainModels = dbModels.map { it.toDomainModel() }
                Result.success(domainModels)
            }
        } catch (e: Throwable) {
            return locationNetSource.getCities(countryId = countryId)
                .mapCatching { netModels ->
                    val dbModels = netModels.map { it.toDbModel(continentId) }
                    locationDbSource.insertCities(dbModels)
                    dbModels.map { it.toDomainModel() }
                }
        }
    }
}
