package mega.triple.aaa.data.network.response.location


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityKeyResponse(
    @SerialName("Key")
    val key: String?,
)