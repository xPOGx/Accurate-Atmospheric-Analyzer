package mega.triple.aaa.data.local.model.forecast

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class DayNightDbModel(
    @ColumnInfo("cloud_cover")
    val cloudCover: Int?,
    @Embedded("evapotranspiration")
    val evapotranspiration: ValueDbModel?,
    @ColumnInfo("has_precipitation")
    val hasPrecipitation: Boolean?,
    @ColumnInfo("hours_ff_Ice")
    val hoursOfIce: Double?,
    @ColumnInfo("hours_of_precipitation")
    val hoursOfPrecipitation: Double?,
    @ColumnInfo("hours_of_rain")
    val hoursOfRain: Double?,
    @ColumnInfo("hours_of_snow")
    val hoursOfSnow: Double?,
    @Embedded("ice")
    val ice: ValueDbModel?,
    @ColumnInfo("ice_probability")
    val iceProbability: Int?,
    @ColumnInfo("icon")
    val icon: Int?,
    @ColumnInfo("icon_phrase")
    val iconPhrase: String?,
    @ColumnInfo("long_phrase")
    val longPhrase: String?,
    @ColumnInfo("precipitation_probability")
    val precipitationProbability: Int?,
    @Embedded("rain")
    val rain: ValueDbModel?,
    @ColumnInfo("rain_probability")
    val rainProbability: Int?,
    @Embedded("relative_humidity")
    val relativeHumidity: ValueDbModel?,
    @ColumnInfo("short_phrase")
    val shortPhrase: String?,
    @Embedded("snow")
    val snow: ValueDbModel?,
    @ColumnInfo("snow_probability")
    val snowProbability: Int?,
    @Embedded("solar_irradiance")
    val solarIrradiance: ValueDbModel?,
    @ColumnInfo("thunderstorm_probability")
    val thunderstormProbability: Int?,
    @Embedded("total_liquid")
    val totalLiquid: ValueDbModel?,
    @Embedded("wet_bulb_globe_temperature")
    val wetBulbGlobeTemperature: ValueWrapperDbModel?,
    @Embedded("wet_bulb_temperature")
    val wetBulbTemperature: ValueWrapperDbModel?,
    @Embedded("wind")
    val wind: DirectionWrapperDbModel?,
    @Embedded("wind_gust")
    val windGust: DirectionWrapperDbModel?
)
