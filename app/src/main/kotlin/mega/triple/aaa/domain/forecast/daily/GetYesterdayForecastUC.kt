package mega.triple.aaa.domain.forecast.daily

import kotlinx.coroutines.flow.first
import mega.triple.aaa.data.local.source.ForecastDbSource
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import javax.inject.Inject

class GetYesterdayForecastUC @Inject constructor(
    private val dbSource: ForecastDbSource,
) {
    suspend operator fun invoke(): Result<DailyForecastDomainModel> {
        return try {
            val domain = dbSource.getYesterdayForecast().first()?.toDomainModel() ?: throw NullResult()
            Result.success(domain)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
