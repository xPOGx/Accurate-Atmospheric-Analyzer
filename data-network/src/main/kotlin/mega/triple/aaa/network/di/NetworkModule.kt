package mega.triple.aaa.network.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import mega.triple.aaa.network.api.ForecastService
import mega.triple.aaa.network.api.LocationService
import mega.triple.aaa.network.api.impl.ForecastServiceImpl
import mega.triple.aaa.network.api.impl.LocationServiceImpl
import mega.triple.aaa.network.source.ForecastNetSource
import mega.triple.aaa.network.source.LocationNetSource
import mega.triple.aaa.network.source.impl.ForecastNetSourceImpl
import mega.triple.aaa.network.source.impl.LocationNetSourceImpl
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val CONTENT_LENGTH = 250_000L

val dataNetworkModule = module {
    single<Interceptor> {
        ChuckerInterceptor.Builder(androidContext())
            .collector(ChuckerCollector(androidContext()))
            .maxContentLength(CONTENT_LENGTH)
            .alwaysReadResponseBody(true)
            .createShortcut(true)
            .build()
    }

    single<HttpClientEngine> {
        OkHttp.create {
            addInterceptor(get())
        }
    }

    single {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(
                    Json(DefaultJson) {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }


    singleOf(::LocationServiceImpl) bind LocationService::class
    singleOf(::ForecastServiceImpl) bind ForecastService::class

    singleOf(::LocationNetSourceImpl) bind LocationNetSource::class
    singleOf(::ForecastNetSourceImpl) bind ForecastNetSource::class
}
