package mega.triple.aaa.domain.location.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.EmptyDatabase
import mega.triple.aaa.domain.location.GetContinentsUC
import mega.triple.aaa.domain.location.model.ContinentDomainModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.LocationDbSource
import mega.triple.aaa.network.source.LocationNetSource
import javax.inject.Inject

class GetContinentsUCImpl @Inject constructor(
    private val locationDbSource: LocationDbSource,
    private val locationNetSource: LocationNetSource,
) : GetContinentsUC {
    override suspend operator fun invoke(): Result<List<ContinentDomainModel>> {
        return try {
            val dbModels = locationDbSource.getContinents().first()
            if (dbModels.isEmpty()) {
                throw EmptyDatabase()
            } else {
                val domainModels = dbModels.map { it.toDomainModel() }
                Result.success(domainModels)
            }
        } catch (e: Throwable) {
            return locationNetSource.getContinents()
                .mapCatching { netModels ->
                    val dbModels = netModels.map { it.toDbModel() }
                    locationDbSource.insertContinents(dbModels)
                    dbModels.map { it.toDomainModel() }
                }
        }
    }
}
