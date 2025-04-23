package mega.triple.aaa.domain.location.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.resultLauncher
import mega.triple.aaa.domain.location.GetCityKeyUC
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.SetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toProtoModel
import mega.triple.aaa.proto.LocationDataStore

class SetLocationUCImpl(
    private val locationDataStore: LocationDataStore,
    private val getCityKeyUC: GetCityKeyUC,
    private val getLocationUC: GetLocationUC,
) : SetLocationUC {
    override suspend operator fun invoke(domainModel: LocationDomainModel): Result<Unit> =
        resultLauncher {
            val location = getLocationUC().first()
            if (location?.continent?.id == domainModel.continent?.id &&
                location?.country?.id == domainModel.country?.id &&
                location?.city?.id == domainModel.city?.id
            ) {
                return@resultLauncher
            }

            getCityKeyUC(
                countryId = domainModel.country!!.id,
                cityId = domainModel.city!!.id!!,
                cityName = domainModel.city.englishName!!,
            ).onSuccess { key ->
                val newModel = domainModel.copy(
                    city = domainModel.city.copy(
                        locationKey = key
                    )
                )
                locationDataStore.saveLocation(newModel.toProtoModel())
            }.onFailure {
                throw it
            }
        }
}
