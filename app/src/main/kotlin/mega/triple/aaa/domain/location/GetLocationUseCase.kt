package mega.triple.aaa.domain.location

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import mega.triple.aaa.data.proto.LocationDataStore
import mega.triple.aaa.domain.location.model.LocationDomainModel
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toDomainModel
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationDataStore: LocationDataStore,
) {
    operator fun invoke(): Flow<LocationDomainModel?> {
        return try {
            val protoModel = locationDataStore.readLocation()
            protoModel.map { it?.toDomainModel() }
        } catch (e: Exception) {
            flowOf(null)
        }
    }
}