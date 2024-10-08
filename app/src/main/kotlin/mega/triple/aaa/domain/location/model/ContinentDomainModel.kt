package mega.triple.aaa.domain.location.model

import mega.triple.aaa.data.local.model.location.ContinentDbModel
import mega.triple.aaa.data.network.response.location.ContinentResponse
import mega.triple.aaa.data.proto.ContinentProto
import mega.triple.aaa.domain.ext.validateNotNull
import mega.triple.aaa.presentation.core.ui.model.ContinentUiModel

data class ContinentDomainModel(
    val id: String,
    val englishName: String?,
    val localizedName: String?,
) {
    companion object {
        fun ContinentDbModel.toDomainModel(): ContinentDomainModel =
            ContinentDomainModel(
                id = id,
                englishName = englishName,
                localizedName = localizedName,
            )

        fun ContinentResponse.toDbModel(): ContinentDbModel =
            ContinentDbModel(
                id = validateNotNull(id),
                englishName = englishName,
                localizedName = localizedName,
            )

        fun ContinentDomainModel.toProtoModel(): ContinentProto =
            ContinentProto.newBuilder()
                .setId(id)
                .setEnglishName(englishName)
                .setLocalizedName(localizedName)
                .build()

        fun ContinentProto.toDomainModel(): ContinentDomainModel =
            ContinentDomainModel(
                id = id,
                englishName = englishName,
                localizedName = localizedName,
            )

        fun ContinentUiModel.toDomainModel(): ContinentDomainModel =
            ContinentDomainModel(
                id = id,
                englishName = englishName,
                localizedName = localizedName,
            )
    }
}