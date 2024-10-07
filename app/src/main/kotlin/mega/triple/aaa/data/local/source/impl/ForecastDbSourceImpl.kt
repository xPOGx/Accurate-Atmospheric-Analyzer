package mega.triple.aaa.data.local.source.impl

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.dao.forecast.DailyForecastDao
import mega.triple.aaa.data.local.model.forecast.daily.DailyForecastDbModel
import mega.triple.aaa.data.local.source.ForecastDbSource
import javax.inject.Inject

class ForecastDbSourceImpl @Inject constructor(
    private val dailyForecastDao: DailyForecastDao,
) : ForecastDbSource {
    override fun getDailyForecasts(time: Long): Flow<List<DailyForecastDbModel>> =
        dailyForecastDao.getDailyForecast(time)

    override suspend fun insertDailyForecasts(list: List<DailyForecastDbModel>) =
        dailyForecastDao.insertDailyForecasts(list)
}