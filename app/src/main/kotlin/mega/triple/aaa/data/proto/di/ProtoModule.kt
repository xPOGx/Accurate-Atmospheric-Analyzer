package mega.triple.aaa.data.proto.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mega.triple.aaa.data.proto.LocationDataStore
import mega.triple.aaa.data.proto.impl.LocationDataStoreImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ProtoModule {
    @Binds
    abstract fun bindLocationDataStore(dataStore: LocationDataStoreImpl): LocationDataStore
}