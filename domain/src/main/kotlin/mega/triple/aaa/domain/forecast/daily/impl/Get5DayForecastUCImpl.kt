package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.forecast.daily.Get5DayForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.ForecastDbSource

class Get5DayForecastUCImpl(
    private val dbSource: ForecastDbSource,
) : Get5DayForecastUC {
    override suspend operator fun invoke(): Result<List<DailyForecastDomainModel>> {
        return try {
            val domain = dbSource.getDailyForecasts().first().map { it.toDomainModel() }
            Result.success(domain)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
