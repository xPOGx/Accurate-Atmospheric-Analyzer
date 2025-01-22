package mega.triple.aaa.proto

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.proto.LocationProto

interface LocationDataStore {
    suspend fun saveLocation(locationProto: LocationProto)

    fun readLocation(): Flow<LocationProto?>
}