package mega.triple.aaa.domain.location.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toDomainModel
import mega.triple.aaa.proto.LocationDataStore
import javax.inject.Inject

class GetLocationUCImpl @Inject constructor(
    private val locationDataStore: LocationDataStore,
) : GetLocationUC {
    override operator fun invoke(): Flow<LocationDomainModel?> {
        return try {
            val protoModel = locationDataStore.readLocation()
            protoModel.map { it?.toDomainModel() }
        } catch (e: Exception) {
            flowOf(null)
        }
    }
}
