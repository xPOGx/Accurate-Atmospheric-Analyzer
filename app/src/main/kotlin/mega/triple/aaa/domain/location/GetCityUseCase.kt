package mega.triple.aaa.domain.location

import kotlinx.coroutines.flow.first
import mega.triple.aaa.data.local.source.LocationDbSource
import mega.triple.aaa.data.network.source.LocationNetSource
import mega.triple.aaa.domain.location.model.CityDomainModel
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toDomainModel
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val locationDbSource: LocationDbSource,
    private val locationNetSource: LocationNetSource,
) {
    suspend operator fun invoke(countryId: String): Result<List<CityDomainModel>> {
        return try {
            val dbModels = locationDbSource.getCities(countryId).first()
            if (dbModels.isEmpty()) {
                throw IllegalStateException("Database is empty")
            } else {
                val domainModels = dbModels.map { it.toDomainModel() }
                Result.success(domainModels)
            }
        } catch (e: Exception) {
            return locationNetSource.getCities(countryId = countryId)
                .mapCatching { netModels ->
                    val dbModels = netModels.map { it.toDbModel(countryId) }
                    locationDbSource.insertCities(dbModels)
                    dbModels.map { it.toDomainModel() }
                }
        }
    }
}