package mega.triple.aaa.data.local.source

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.model.forecast.daily.DailyForecastDbModel

interface ForecastDbSource {
    fun getDailyForecasts(): Flow<List<DailyForecastDbModel>>
    suspend fun insertDailyForecasts(list: List<DailyForecastDbModel>)

    fun getTodayForecast(): Flow<DailyForecastDbModel?>

    fun getTomorrowForecast(): Flow<DailyForecastDbModel?>

    fun getYesterdayForecast(): Flow<DailyForecastDbModel?>
}