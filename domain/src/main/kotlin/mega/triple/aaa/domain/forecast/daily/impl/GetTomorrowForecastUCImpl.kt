package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.ext.resultLauncher
import mega.triple.aaa.domain.forecast.daily.GetTomorrowForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.ForecastDbSource

class GetTomorrowForecastUCImpl(
    private val dbSource: ForecastDbSource,
) : GetTomorrowForecastUC {
    override suspend operator fun invoke(): Result<DailyForecastDomainModel> =
        resultLauncher {
            dbSource.getTomorrowForecast().first()?.toDomainModel() ?: throw NullResult()
        }
}
