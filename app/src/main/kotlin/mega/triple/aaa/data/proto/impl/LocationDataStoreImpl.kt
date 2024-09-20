package mega.triple.aaa.data.proto.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.proto.LocationDataStore
import mega.triple.aaa.data.proto.LocationProto
import mega.triple.aaa.data.proto.serializer.LocationProtoSerializer
import javax.inject.Inject

class LocationDataStoreImpl @Inject constructor(
    @ApplicationContext context: Context,
) : LocationDataStore {
    private val dataStore: DataStore<LocationProto> = context.locationDataStore

    override suspend fun saveLocation(locationProto: LocationProto) {
        dataStore.updateData {
            locationProto
        }
    }

    override fun readLocation(): Flow<LocationProto> = dataStore.data

    companion object {
        private val Context.locationDataStore: DataStore<LocationProto> by dataStore(
            fileName = "",
            serializer = LocationProtoSerializer,
        )
    }
}