package mega.triple.aaa.data.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import mega.triple.aaa.data.network.api.LocationService
import mega.triple.aaa.data.network.api.impl.LocationServiceImpl
import mega.triple.aaa.data.network.source.LocationSource
import mega.triple.aaa.data.network.source.impl.LocationSourceImpl
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_LENGTH = 250_000L

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
    ): Interceptor =
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(CONTENT_LENGTH)
            .alwaysReadResponseBody(true)
            .createShortcut(true)
            .build()

    @Provides
    fun provideOkHttpEngine(
        interceptor: Interceptor
    ): HttpClientEngine =
        OkHttp.create {
            addInterceptor(interceptor)
        }

    @Provides
    fun provideHttpClient(
        httpClientEngine: HttpClientEngine
    ): HttpClient =
        HttpClient(httpClientEngine) {
            install(ContentNegotiation) {
                json()
            }
        }

    @Provides
    fun provideLocationService(
        httpClient: HttpClient
    ): LocationService = LocationServiceImpl(httpClient)

    @Provides
    fun provideLocationSource(
        apiService: LocationService
    ): LocationSource = LocationSourceImpl(apiService)
}
