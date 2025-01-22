package mega.triple.aaa.network.response.daily


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastWrapper(
    @SerialName("DailyForecasts")
    val dailyForecasts: List<DailyForecastResponse>?,
)