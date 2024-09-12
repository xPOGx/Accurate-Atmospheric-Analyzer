package mega.triple.aaa.domain.location

import kotlinx.coroutines.flow.first
import mega.triple.aaa.data.local.source.LocationDbSource
import mega.triple.aaa.data.network.source.LocationNetSource
import mega.triple.aaa.domain.ext.DB_EMPTY
import mega.triple.aaa.domain.location.model.CountryDomainModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toDomainModel
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val locationDbSource: LocationDbSource,
    private val locationNetSource: LocationNetSource,
) {
    suspend operator fun invoke(continentId: String): Result<List<CountryDomainModel>> {
        return try {
            val dbModels = locationDbSource.getCountries(continentId).first()
            if (dbModels.isEmpty()) {
                throw IllegalStateException(DB_EMPTY)
            } else {
                val domainModels = dbModels.map { it.toDomainModel() }
                Result.success(domainModels)
            }
        } catch (e: Exception) {
            return locationNetSource.getCountries(continentId = continentId)
                .mapCatching { netModels ->
                    val dbModels = netModels.map { it.toDbModel(continentId) }
                    locationDbSource.insertCountries(dbModels)
                    dbModels.map { it.toDomainModel() }
                }
        }
    }
}