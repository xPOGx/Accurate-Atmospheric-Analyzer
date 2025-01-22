package mega.triple.aaa.local.source

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.local.model.forecast.daily.DailyForecastDbModel

interface ForecastDbSource {
    fun getDailyForecasts(): Flow<List<DailyForecastDbModel>>
    suspend fun insertDailyForecasts(list: List<DailyForecastDbModel>)

    fun getTodayForecast(): Flow<DailyForecastDbModel?>

    fun getTomorrowForecast(): Flow<DailyForecastDbModel?>

    fun getYesterdayForecast(): Flow<DailyForecastDbModel?>
}