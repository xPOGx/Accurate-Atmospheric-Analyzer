package mega.triple.aaa.proto.di

import mega.triple.aaa.proto.LocationDataStore
import mega.triple.aaa.proto.impl.LocationDataStoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataProtoModule = module {
    single<LocationDataStore> { LocationDataStoreImpl(androidContext()) }
}
