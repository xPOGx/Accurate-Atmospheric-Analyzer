package mega.triple.aaa.domain.location

import mega.triple.aaa.data.proto.LocationDataStore
import mega.triple.aaa.domain.location.model.LocationDomainModel
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toProtoModel
import javax.inject.Inject

class SetLocationUseCase @Inject constructor(
    private val locationDataStore: LocationDataStore,
    private val getCityKeyUseCase: GetCityKeyUseCase,
) {
    suspend operator fun invoke(domainModel: LocationDomainModel): Result<Unit> {
        return try {
            getCityKeyUseCase(
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

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}