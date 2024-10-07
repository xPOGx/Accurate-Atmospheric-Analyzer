package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayNightResponse(
    @SerialName("CloudCover")
    val cloudCover: Int?,
    @SerialName("Evapotranspiration")
    val evapotranspiration: ValueResponse?,
    @SerialName("HasPrecipitation")
    val hasPrecipitation: Boolean?,
    @SerialName("HoursOfIce")
    val hoursOfIce: Double?,
    @SerialName("HoursOfPrecipitation")
    val hoursOfPrecipitation: Double?,
    @SerialName("HoursOfRain")
    val hoursOfRain: Double?,
    @SerialName("HoursOfSnow")
    val hoursOfSnow: Double?,
    @SerialName("Ice")
    val ice: ValueResponse?,
    @SerialName("IceProbability")
    val iceProbability: Int?,
    @SerialName("Icon")
    val icon: Int?,
    @SerialName("IconPhrase")
    val iconPhrase: String?,
    @SerialName("LongPhrase")
    val longPhrase: String?,
    @SerialName("PrecipitationProbability")
    val precipitationProbability: Int?,
    @SerialName("Rain")
    val rain: ValueResponse?,
    @SerialName("RainProbability")
    val rainProbability: Int?,
    @SerialName("RelativeHumidity")
    val relativeHumidity: ValueWrapper?,
    @SerialName("ShortPhrase")
    val shortPhrase: String?,
    @SerialName("Snow")
    val snow: ValueResponse?,
    @SerialName("SnowProbability")
    val snowProbability: Int?,
    @SerialName("SolarIrradiance")
    val solarIrradiance: ValueResponse?,
    @SerialName("ThunderstormProbability")
    val thunderstormProbability: Int?,
    @SerialName("TotalLiquid")
    val totalLiquid: ValueResponse?,
    @SerialName("WetBulbGlobeTemperature")
    val wetBulbGlobeTemperature: ValueWrapper?,
    @SerialName("WetBulbTemperature")
    val wetBulbTemperature: ValueWrapper?,
    @SerialName("Wind")
    val wind: DirectionWrapper?,
    @SerialName("WindGust")
    val windGust: DirectionWrapper?
)