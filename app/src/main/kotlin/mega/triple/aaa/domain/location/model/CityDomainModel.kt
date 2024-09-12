package mega.triple.aaa.domain.location.model

import mega.triple.aaa.data.local.model.CityDbModel
import mega.triple.aaa.data.network.response.CityResponse
import mega.triple.aaa.domain.ext.validateNotNull

data class CityDomainModel(
    val id: String,
    val countryID: String,
    val englishName: String?,
    val englishType: String?,
    val level: Int?,
    val localizedName: String?,
    val localizedType: String?,
) {
    companion object {
        fun CityDbModel.toDomainModel(): CityDomainModel =
            CityDomainModel(
                id = id,
                countryID = countryID,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )

        fun CityResponse.toDbModel(countryID: String): CityDbModel =
            CityDbModel(
                id = validateNotNull(id),
                countryID = countryID,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )
    }
}