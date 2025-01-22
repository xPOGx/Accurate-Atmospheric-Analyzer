package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.forecast.daily.GetTomorrowForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.ForecastDbSource
import javax.inject.Inject

class GetTomorrowForecastUCImpl @Inject constructor(
    private val dbSource: ForecastDbSource,
) : GetTomorrowForecastUC {
    override suspend operator fun invoke(): Result<DailyForecastDomainModel> {
        return try {
            val domain = dbSource.getTomorrowForecast().first()?.toDomainModel() ?: throw NullResult()
            Result.success(domain)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
