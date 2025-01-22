package mega.triple.aaa.domain.location.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.EmptyDatabase
import mega.triple.aaa.domain.location.GetCountriesUC
import mega.triple.aaa.domain.location.model.CountryDomainModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.LocationDbSource
import mega.triple.aaa.network.source.LocationNetSource
import javax.inject.Inject

class GetCountriesUCImpl @Inject constructor(
    private val locationDbSource: LocationDbSource,
    private val locationNetSource: LocationNetSource,
) : GetCountriesUC {
    override suspend operator fun invoke(continentId: String): Result<List<CountryDomainModel>> {
        return try {
            val dbModels = locationDbSource.getCountries(continentId).first()
            if (dbModels.isEmpty()) {
                throw EmptyDatabase()
            } else {
                val domainModels = dbModels.map { it.toDomainModel() }
                Result.success(domainModels)
            }
        } catch (e: Throwable) {
            return locationNetSource.getCountries(continentId = continentId)
                .mapCatching { netModels ->
                    val dbModels = netModels.map { it.toDbModel(continentId) }
                    locationDbSource.insertCountries(dbModels)
                    dbModels.map { it.toDomainModel() }
                }
        }
    }
}
