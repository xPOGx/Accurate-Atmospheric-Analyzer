package mega.triple.aaa.domain.location.impl

import mega.triple.aaa.domain.ext.validateNotNull
import mega.triple.aaa.domain.location.GetCityKeyUC
import mega.triple.aaa.network.source.LocationNetSource
import javax.inject.Inject

class GetCityKeyUCImpl @Inject constructor(
    private val locationNetSource: LocationNetSource,
) : GetCityKeyUC {
    override suspend operator fun invoke(
        countryId: String,
        cityId: String,
        cityName: String,
    ): Result<String> = locationNetSource.getCityKey(countryId, cityId, cityName)
        .mapCatching { validateNotNull(it.first().key) }
}