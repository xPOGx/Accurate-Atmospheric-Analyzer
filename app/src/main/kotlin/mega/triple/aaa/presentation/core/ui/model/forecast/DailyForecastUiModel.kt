package mega.triple.aaa.presentation.core.ui.model.forecast

import android.icu.util.Calendar

data class DailyForecastUiModel(
    val airAndPollen: List<CategoryUiModel>?,
    val date: String?,
    val day: DayNightUiModel?,
    val epochDate: Long,
    val hoursOfSun: Double?,
    val moon: SunMoonUiModel?,
    val night: DayNightUiModel?,
    val realFeelTemperature: ValueWrapperUiModel?,
    val realFeelTemperatureShade: ValueWrapperUiModel?,
    val sun: SunMoonUiModel?,
    val temperature: ValueWrapperUiModel?,
) {
    val isDay by lazy {
        val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        hourOfDay >= (sun?.timeRise?.toIntOrNull() ?: 6)
                && hourOfDay < (sun?.timeSet?.toIntOrNull() ?: 18)
    }

    val airQuality by lazy { airAndPollen?.firstOrNull { it.name == AIR_QUALITY_KEY }?.category }

    val uvIndex by lazy { airAndPollen?.firstOrNull { it.name == UV_INDEX_KEY }?.value }

    companion object {
        private const val AIR_QUALITY_KEY = "AirQuality"
        private const val UV_INDEX_KEY = "UVIndex"
    }
}
