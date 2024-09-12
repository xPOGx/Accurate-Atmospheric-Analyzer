package mega.triple.aaa.domain.location

import kotlinx.coroutines.flow.first
import mega.triple.aaa.data.local.source.LocationDbSource
import mega.triple.aaa.data.network.source.LocationNetSource
import mega.triple.aaa.domain.ext.DB_EMPTY
import mega.triple.aaa.domain.location.model.ContinentDomainModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toDomainModel
import javax.inject.Inject

class GetContinentsUseCase @Inject constructor(
    private val locationDbSource: LocationDbSource,
    private val locationNetSource: LocationNetSource,
) {
    suspend operator fun invoke(): Result<List<ContinentDomainModel>> {
        return try {
            val dbModels = locationDbSource.getContinents().first()
            if (dbModels.isEmpty()) {
                throw IllegalStateException(DB_EMPTY)
            } else {
                val domainModels = dbModels.map { it.toDomainModel() }
                Result.success(domainModels)
            }
        } catch (e: Exception) {
            return locationNetSource.getContinents()
                .mapCatching { netModels ->
                    val dbModels = netModels.map { it.toDbModel() }
                    locationDbSource.insertContinents(dbModels)
                    dbModels.map { it.toDomainModel() }
                }
        }
    }
}