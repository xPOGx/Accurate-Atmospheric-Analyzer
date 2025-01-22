package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.forecast.daily.GetTodayForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.ForecastDbSource
import javax.inject.Inject

class GetTodayForecastUCImpl @Inject constructor(
    private val dbSource: ForecastDbSource,
) : GetTodayForecastUC {
    override suspend operator fun invoke(): Result<DailyForecastDomainModel> {
        return try {
            val domain = dbSource.getTodayForecast().first()?.toDomainModel() ?: throw NullResult()
            Result.success(domain)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
