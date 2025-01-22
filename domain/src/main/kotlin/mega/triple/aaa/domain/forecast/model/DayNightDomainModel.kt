package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.domain.forecast.model.DirectionWrapperDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.DirectionWrapperDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.DirectionWrapperDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.forecast.model.RelativeHumidityDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.RelativeHumidityDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.RelativeHumidityDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toUiModel
import mega.triple.aaa.local.model.forecast.DayNightDbModel
import mega.triple.aaa.network.response.model.DayNightResponse
import mega.triple.aaa.ui.model.forecast.DayNightUiModel

data class DayNightDomainModel(
    val cloudCover: Int?,
    val evapotranspiration: ValueDomainModel?,
    val hasPrecipitation: Boolean?,
    val hoursOfIce: Double?,
    val hoursOfPrecipitation: Double?,
    val hoursOfRain: Double?,
    val hoursOfSnow: Double?,
    val ice: ValueDomainModel?,
    val iceProbability: Int?,
    val icon: Int?,
    val iconPhrase: String?,
    val longPhrase: String?,
    val precipitationProbability: Int?,
    val rain: ValueDomainModel?,
    val rainProbability: Int?,
    val relativeHumidity: RelativeHumidityDomainModel?,
    val shortPhrase: String?,
    val snow: ValueDomainModel?,
    val snowProbability: Int?,
    val solarIrradiance: ValueDomainModel?,
    val thunderstormProbability: Int?,
    val totalLiquid: ValueDomainModel?,
    val wetBulbGlobeTemperature: ValueWrapperDomainModel?,
    val wetBulbTemperature: ValueWrapperDomainModel?,
    val wind: DirectionWrapperDomainModel?,
    val windGust: DirectionWrapperDomainModel?
) {
    companion object {
        fun DayNightResponse.toDbModel(): DayNightDbModel =
            DayNightDbModel(
                cloudCover = cloudCover,
                evapotranspiration = evapotranspiration?.toDbModel(),
                hasPrecipitation = hasPrecipitation,
                hoursOfIce = hoursOfIce,
                hoursOfPrecipitation = hoursOfPrecipitation,
                hoursOfRain = hoursOfRain,
                hoursOfSnow = hoursOfSnow,
                ice = ice?.toDbModel(),
                iceProbability = iceProbability,
                icon = icon,
                iconPhrase = iconPhrase,
                longPhrase = longPhrase,
                precipitationProbability = precipitationProbability,
                rain = rain?.toDbModel(),
                rainProbability = rainProbability,
                relativeHumidity = relativeHumidity?.toDbModel(),
                shortPhrase = shortPhrase,
                snow = snow?.toDbModel(),
                snowProbability = snowProbability,
                solarIrradiance = solarIrradiance?.toDbModel(),
                thunderstormProbability = thunderstormProbability,
                totalLiquid = totalLiquid?.toDbModel(),
                wetBulbGlobeTemperature = wetBulbGlobeTemperature?.toDbModel(),
                wetBulbTemperature = wetBulbTemperature?.toDbModel(),
                wind = wind?.toDbModel(),
                windGust = windGust?.toDbModel(),
            )

        fun DayNightResponse.toDomainModel(): DayNightDomainModel =
            DayNightDomainModel(
                cloudCover = cloudCover,
                evapotranspiration = evapotranspiration?.toDomainModel(),
                hasPrecipitation = hasPrecipitation,
                hoursOfIce = hoursOfIce,
                hoursOfPrecipitation = hoursOfPrecipitation,
                hoursOfRain = hoursOfRain,
                hoursOfSnow = hoursOfSnow,
                ice = ice?.toDomainModel(),
                iceProbability = iceProbability,
                icon = icon,
                iconPhrase = iconPhrase,
                longPhrase = longPhrase,
                precipitationProbability = precipitationProbability,
                rain = rain?.toDomainModel(),
                rainProbability = rainProbability,
                relativeHumidity = relativeHumidity?.toDomainModel(),
                shortPhrase = shortPhrase,
                snow = snow?.toDomainModel(),
                snowProbability = snowProbability,
                solarIrradiance = solarIrradiance?.toDomainModel(),
                thunderstormProbability = thunderstormProbability,
                totalLiquid = totalLiquid?.toDomainModel(),
                wetBulbGlobeTemperature = wetBulbGlobeTemperature?.toDomainModel(),
                wetBulbTemperature = wetBulbTemperature?.toDomainModel(),
                wind = wind?.toDomainModel(),
                windGust = windGust?.toDomainModel(),
            )

        fun DayNightDbModel.toDomainModel(): DayNightDomainModel =
            DayNightDomainModel(
                cloudCover = cloudCover,
                evapotranspiration = evapotranspiration?.toDomainModel(),
                hasPrecipitation = hasPrecipitation,
                hoursOfIce = hoursOfIce,
                hoursOfPrecipitation = hoursOfPrecipitation,
                hoursOfRain = hoursOfRain,
                hoursOfSnow = hoursOfSnow,
                ice = ice?.toDomainModel(),
                iceProbability = iceProbability,
                icon = icon,
                iconPhrase = iconPhrase,
                longPhrase = longPhrase,
                precipitationProbability = precipitationProbability,
                rain = rain?.toDomainModel(),
                rainProbability = rainProbability,
                relativeHumidity = relativeHumidity?.toDomainModel(),
                shortPhrase = shortPhrase,
                snow = snow?.toDomainModel(),
                snowProbability = snowProbability,
                solarIrradiance = solarIrradiance?.toDomainModel(),
                thunderstormProbability = thunderstormProbability,
                totalLiquid = totalLiquid?.toDomainModel(),
                wetBulbGlobeTemperature = wetBulbGlobeTemperature?.toDomainModel(),
                wetBulbTemperature = wetBulbTemperature?.toDomainModel(),
                wind = wind?.toDomainModel(),
                windGust = windGust?.toDomainModel(),
            )

        fun DayNightDomainModel.toUiModel(): DayNightUiModel =
            DayNightUiModel(
                cloudCover = cloudCover,
                evapotranspiration = evapotranspiration?.toUiModel(),
                hasPrecipitation = hasPrecipitation,
                hoursOfIce = hoursOfIce,
                hoursOfPrecipitation = hoursOfPrecipitation,
                hoursOfRain = hoursOfRain,
                hoursOfSnow = hoursOfSnow,
                ice = ice?.toUiModel(),
                iceProbability = iceProbability,
                icon = icon,
                iconPhrase = iconPhrase,
                longPhrase = longPhrase,
                precipitationProbability = precipitationProbability,
                rain = rain?.toUiModel(),
                rainProbability = rainProbability,
                relativeHumidity = relativeHumidity?.toUiModel(),
                shortPhrase = shortPhrase,
                snow = snow?.toUiModel(),
                snowProbability = snowProbability,
                solarIrradiance = solarIrradiance?.toUiModel(),
                thunderstormProbability = thunderstormProbability,
                totalLiquid = totalLiquid?.toUiModel(),
                wetBulbGlobeTemperature = wetBulbGlobeTemperature?.toUiModel(),
                wetBulbTemperature = wetBulbTemperature?.toUiModel(),
                wind = wind?.toUiModel(),
                windGust = windGust?.toUiModel(),
            )
    }
}
