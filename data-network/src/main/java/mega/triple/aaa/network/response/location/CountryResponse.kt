package mega.triple.aaa.network.response.location

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