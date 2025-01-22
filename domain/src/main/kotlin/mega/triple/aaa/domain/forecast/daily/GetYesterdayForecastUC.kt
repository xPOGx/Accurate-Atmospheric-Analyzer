package mega.triple.aaa.domain.forecast.daily

import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel

interface GetYesterdayForecastUC {
    suspend operator fun invoke(): Result<DailyForecastDomainModel>
}
