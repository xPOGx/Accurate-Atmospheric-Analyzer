package mega.triple.aaa.domain.location

import mega.triple.aaa.data.network.source.LocationNetSource
import mega.triple.aaa.domain.ext.validateNotNull
import javax.inject.Inject

class GetCityKeyUseCase @Inject constructor(
    private val locationNetSource: LocationNetSource,
) {
    suspend operator fun invoke(
        countryId: String,
        cityId: String,
        cityName: String,
    ): Result<String> {
        return locationNetSource.getCityKey(countryId, cityId, cityName)
            .mapCatching { validateNotNull(it.first().key) }
    }
}