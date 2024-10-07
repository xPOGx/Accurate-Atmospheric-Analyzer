package mega.triple.aaa.domain.location.model

import mega.triple.aaa.data.local.model.location.CityDbModel
import mega.triple.aaa.data.network.response.location.CityResponse
import mega.triple.aaa.data.proto.CityProto
import mega.triple.aaa.presentation.core.common.Constants.UNDERSCORE
import mega.triple.aaa.presentation.core.ui.model.CityUiModel

data class CityDomainModel(
    val id: String?,
    val locationKey: String?,
    val countryID: String?,
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
                locationKey = locationKey,
                countryID = countryID,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )

        fun CityResponse.toDbModel(continentId: String): CityDbModel =
            CityDbModel(
                dbId = createUuid(continentId, countryID, id),
                id = id,
                locationKey = null,
                countryID = countryID,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )

        fun CityDomainModel.toProtoModel(): CityProto =
            CityProto.newBuilder()
                .setId(id)
                .setLocationKey(locationKey)
                .setCountryId(countryID)
                .setEnglishName(englishName)
                .setEnglishType(englishType)
                .setLevel(level ?: 0)
                .setLocalizedName(localizedName)
                .setLocalizedType(localizedType)
                .build()

        fun CityProto.toDomainModel(): CityDomainModel =
            CityDomainModel(
                id = id,
                locationKey = locationKey,
                countryID = countryId,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )

        fun CityUiModel.toDomainModel(): CityDomainModel =
            CityDomainModel(
                id = id,
                locationKey = locationKey,
                countryID = countryID,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )

        private fun createUuid(continentId: String, countryId: String?, cityId: String?) =
            buildString {
                append(continentId)
                append(UNDERSCORE)
                append(countryId)
                append(UNDERSCORE)
                append(cityId)
            }
    }
}