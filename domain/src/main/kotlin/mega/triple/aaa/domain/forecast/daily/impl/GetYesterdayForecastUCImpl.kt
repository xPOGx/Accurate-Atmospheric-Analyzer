package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.firstOrNull
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.ext.resultLauncher
import mega.triple.aaa.domain.forecast.daily.GetYesterdayForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDomainModel
import mega.triple.aaa.local.source.ForecastDbSource

class GetYesterdayForecastUCImpl(
    private val dbSource: ForecastDbSource,
) : GetYesterdayForecastUC {
    override suspend operator fun invoke(): Result<DailyForecastDomainModel> =
        resultLauncher {
            dbSource.getYesterdayForecast().firstOrNull()?.toDomainModel() ?: throw NullResult()
        }
}
