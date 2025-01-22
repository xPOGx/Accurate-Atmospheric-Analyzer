package mega.triple.aaa.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SunMoonResponse(
    @SerialName("Age")
    val age: Int? = null,
    @SerialName("EpochRise")
    val epochRise: Long?,
    @SerialName("EpochSet")
    val epochSet: Long?,
    @SerialName("Phase")
    val phase: String? = null,
    @SerialName("Rise")
    val timeRise: String?,
    @SerialName("Set")
    val timeSet: String?
)