package mega.triple.aaa.data.network.response.location

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(
    @SerialName("EnglishName")
    val englishName: String?,
    @SerialName("ID")
    val id: String?,
    @SerialName("LocalizedName")
    val localizedName: String?
)