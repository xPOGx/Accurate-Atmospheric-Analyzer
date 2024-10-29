package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.daily.DailyForecastDbModel
import mega.triple.aaa.data.network.response.daily.DailyForecastResponse
import mega.triple.aaa.domain.ext.validateNotNull
import mega.triple.aaa.domain.forecast.model.CategoryDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.CategoryDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.DayNightDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.DayNightDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.SunMoonDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.SunMoonDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toDomainModel

data class DailyForecastDomainModel(
    val airAndPollen: List<CategoryDomainModel>?,
    val date: String?,
    val day: DayNightDomainModel?,
    val epochDate: Long,
    val hoursOfSun: Double?,
    val moonResponse: SunMoonDomainModel?,
    val night: DayNightDomainModel?,
    val realFeelTemperature: ValueWrapperDomainModel?,
    val realFeelTemperatureShade: ValueWrapperDomainModel?,
    val sun: SunMoonDomainModel?,
    val temperature: ValueWrapperDomainModel?,
) {
    companion object {
        fun DailyForecastResponse.toDbModel(): DailyForecastDbModel =
            DailyForecastDbModel(
                airAndPollen = airAndPollen?.map { it.toDbModel() },
                date = date,
                day = day?.toDbModel(),
                epochDate = validateNotNull(epochDate),
                hoursOfSun = hoursOfSun,
                moonResponse = moonResponse?.toDbModel(),
                night = night?.toDbModel(),
                realFeelTemperature = realFeelTemperature?.toDbModel(),
                realFeelTemperatureShade = realFeelTemperatureShade?.toDbModel(),
                sun = sun?.toDbModel(),
                temperature = temperature?.toDbModel(),
            )

        fun DailyForecastResponse.toDomainModel(): DailyForecastDomainModel =
            DailyForecastDomainModel(
                airAndPollen = airAndPollen?.map { it.toDomainModel() },
                date = date,
                day = day?.toDomainModel(),
                epochDate = validateNotNull(epochDate),
                hoursOfSun = hoursOfSun,
                moonResponse = moonResponse?.toDomainModel(),
                night = night?.toDomainModel(),
                realFeelTemperature = realFeelTemperature?.toDomainModel(),
                realFeelTemperatureShade = realFeelTemperatureShade?.toDomainModel(),
                sun = sun?.toDomainModel(),
                temperature = temperature?.toDomainModel(),
            )

        fun DailyForecastDbModel.toDomainModel(): DailyForecastDomainModel =
            DailyForecastDomainModel(
                airAndPollen = airAndPollen?.map { it.toDomainModel() },
                date = date,
                day = day?.toDomainModel(),
                epochDate = epochDate,
                hoursOfSun = hoursOfSun,
                moonResponse = moonResponse?.toDomainModel(),
                night = night?.toDomainModel(),
                realFeelTemperature = realFeelTemperature?.toDomainModel(),
                realFeelTemperatureShade = realFeelTemperatureShade?.toDomainModel(),
                sun = sun?.toDomainModel(),
                temperature = temperature?.toDomainModel(),
            )
    }
}