package mega.triple.aaa.network.response.location


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityKeyResponse(
    @SerialName("Key")
    val key: String?,
)