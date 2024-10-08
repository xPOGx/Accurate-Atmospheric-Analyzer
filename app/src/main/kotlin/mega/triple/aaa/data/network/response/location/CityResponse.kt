package mega.triple.aaa.data.network.response.location


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    @SerialName("CountryID")
    val countryID: String?,
    @SerialName("EnglishName")
    val englishName: String?,
    @SerialName("EnglishType")
    val englishType: String?,
    @SerialName("ID")
    val id: String?,
    @SerialName("Level")
    val level: Int?,
    @SerialName("LocalizedName")
    val localizedName: String?,
    @SerialName("LocalizedType")
    val localizedType: String?
)