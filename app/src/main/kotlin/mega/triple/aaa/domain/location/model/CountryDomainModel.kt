package mega.triple.aaa.domain.location.model

import mega.triple.aaa.data.local.model.CountryDbModel
import mega.triple.aaa.data.network.response.CountryResponse
import mega.triple.aaa.domain.ext.validateNotNull

data class CountryDomainModel(
    val id: String,
    val continentId: String,
    val englishName: String?,
    val localizedName: String?,
) {
    companion object {
        fun CountryDbModel.toDomainModel(): CountryDomainModel =
            CountryDomainModel(
                id = id,
                continentId = continentId,
                englishName = englishName,
                localizedName = localizedName,
            )

        fun CountryResponse.toDbModel(continentId: String): CountryDbModel =
            CountryDbModel(
                id = validateNotNull(id),
                continentId = continentId,
                englishName = englishName,
                localizedName = localizedName,
            )
    }
}