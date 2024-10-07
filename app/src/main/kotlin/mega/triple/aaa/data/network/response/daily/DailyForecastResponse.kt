package mega.triple.aaa.data.network.response.daily


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mega.triple.aaa.data.network.response.model.CategoryResponse
import mega.triple.aaa.data.network.response.model.DayNightResponse
import mega.triple.aaa.data.network.response.model.SunMoonResponse
import mega.triple.aaa.data.network.response.model.ValueWrapper

@Serializable
data class DailyForecastResponse(
    @SerialName("AirAndPollen")
    val airAndPollen: List<CategoryResponse>?,
    @SerialName("Date")
    val date: String?,
    @SerialName("Day")
    val day: DayNightResponse?,
    @SerialName("EpochDate")
    val epochDate: Int?,
    @SerialName("HoursOfSun")
    val hoursOfSun: Double?,
    @SerialName("Moon")
    val moonResponse: SunMoonResponse?,
    @SerialName("Night")
    val night: DayNightResponse?,
    @SerialName("RealFeelTemperature")
    val realFeelTemperature: ValueWrapper?,
    @SerialName("RealFeelTemperatureShade")
    val realFeelTemperatureShade: ValueWrapper?,
    @SerialName("Sun")
    val sun: SunMoonResponse?,
    @SerialName("Temperature")
    val temperature: ValueWrapper?
)