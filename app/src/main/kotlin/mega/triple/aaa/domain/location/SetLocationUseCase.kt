package mega.triple.aaa.domain.location

import mega.triple.aaa.data.proto.LocationDataStore
import mega.triple.aaa.domain.location.model.LocationDomainModel
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toProtoModel
import javax.inject.Inject

class SetLocationUseCase @Inject constructor(
    private val locationDataStore: LocationDataStore,
) {
    suspend operator fun invoke(domainModel: LocationDomainModel): Result<Unit> {
        return try {
            locationDataStore.saveLocation(domainModel.toProtoModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}