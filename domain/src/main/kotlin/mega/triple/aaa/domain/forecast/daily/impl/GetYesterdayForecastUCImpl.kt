package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.firstOrNull
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.forecast.daily.GetYesterdayForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.ForecastDbSource
import javax.inject.Inject

class GetYesterdayForecastUCImpl @Inject constructor(
    private val dbSource: ForecastDbSource,
) : GetYesterdayForecastUC {
    override suspend operator fun invoke(): Result<DailyForecastDomainModel> {
        return try {
            val domain = dbSource.getYesterdayForecast().firstOrNull()
                ?.toDomainModel()
                ?: throw NullResult()
            Result.success(domain)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
