package mega.triple.aaa.local.source.impl

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.local.dao.forecast.DailyForecastDao
import mega.triple.aaa.local.model.forecast.daily.DailyForecastDbModel
import mega.triple.aaa.local.source.ForecastDbSource

class ForecastDbSourceImpl(
    private val dailyForecastDao: DailyForecastDao,
) : ForecastDbSource {
    override fun getDailyForecasts(): Flow<List<DailyForecastDbModel>> =
        dailyForecastDao.getDailyForecast()

    override suspend fun insertDailyForecasts(list: List<DailyForecastDbModel>) =
        dailyForecastDao.insertDailyForecasts(list)

    override fun getTodayForecast(): Flow<DailyForecastDbModel?> =
        dailyForecastDao.getTodayForecast()

    override fun getTomorrowForecast(): Flow<DailyForecastDbModel?> =
        dailyForecastDao.getTomorrowForecast()

    override fun getYesterdayForecast(): Flow<DailyForecastDbModel?> =
        dailyForecastDao.getYesterdayForecast()
}