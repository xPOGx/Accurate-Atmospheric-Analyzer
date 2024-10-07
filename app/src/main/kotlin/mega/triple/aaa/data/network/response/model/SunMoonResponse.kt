package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SunMoonResponse(
    @SerialName("Age")
    val age: Int?,
    @SerialName("EpochRise")
    val epochRise: Int?,
    @SerialName("EpochSet")
    val epochSet: Int?,
    @SerialName("Phase")
    val phase: String?,
    @SerialName("Rise")
    val timeRise: String?,
    @SerialName("Set")
    val timeSet: String?
)