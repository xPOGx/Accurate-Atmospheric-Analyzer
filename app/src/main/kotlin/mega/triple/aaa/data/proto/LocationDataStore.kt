package mega.triple.aaa.data.proto

import kotlinx.coroutines.flow.Flow

interface LocationDataStore {
    suspend fun saveLocation(locationProto: LocationProto)

    fun readLocation(): Flow<LocationProto>
}