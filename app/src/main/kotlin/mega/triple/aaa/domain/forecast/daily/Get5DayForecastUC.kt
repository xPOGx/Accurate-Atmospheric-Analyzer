package mega.triple.aaa.domain.forecast.daily

import kotlinx.coroutines.flow.first
import mega.triple.aaa.data.local.source.ForecastDbSource
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import javax.inject.Inject

class Get5DayForecastUC @Inject constructor(
    private val dbSource: ForecastDbSource,
) {
    suspend operator fun invoke(): Result<List<DailyForecastDomainModel>> {
        return try {
            val domain = dbSource.getDailyForecasts().first().map { it.toDomainModel() }
            Result.success(domain)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
