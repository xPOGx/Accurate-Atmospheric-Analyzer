package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.daily.DailyForecastDbModel
import mega.triple.aaa.domain.ext.validateNotNull
import mega.triple.aaa.domain.forecast.model.CategoryDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.CategoryDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.CategoryDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.forecast.model.DayNightDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.DayNightDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.DayNightDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.forecast.model.SunMoonDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.SunMoonDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.SunMoonDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.ValueWrapperDomainModel.Companion.toUiModel
import mega.triple.aaa.network.response.daily.DailyForecastResponse
import mega.triple.aaa.presentation.core.ui.model.forecast.DailyForecastUiModel

data class DailyForecastDomainModel(
    val airAndPollen: List<CategoryDomainModel>?,
    val date: String?,
    val day: DayNightDomainModel?,
    val epochDate: Long,
    val hoursOfSun: Double?,
    val moon: SunMoonDomainModel?,
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
                moon = moon?.toDbModel(),
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
                moon = moon?.toDomainModel(),
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
                moon = moon?.toDomainModel(),
                night = night?.toDomainModel(),
                realFeelTemperature = realFeelTemperature?.toDomainModel(),
                realFeelTemperatureShade = realFeelTemperatureShade?.toDomainModel(),
                sun = sun?.toDomainModel(),
                temperature = temperature?.toDomainModel(),
            )

        fun DailyForecastDomainModel.toUiModel(): DailyForecastUiModel =
            DailyForecastUiModel(
                airAndPollen = airAndPollen?.map { it.toUiModel() },
                date = date,
                day = day?.toUiModel(),
                epochDate = epochDate,
                hoursOfSun = hoursOfSun,
                moon = moon?.toUiModel(),
                night = night?.toUiModel(),
                realFeelTemperature = realFeelTemperature?.toUiModel(),
                realFeelTemperatureShade = realFeelTemperatureShade?.toUiModel(),
                sun = sun?.toUiModel(),
                temperature = temperature?.toUiModel(),
            )
    }
}