package mega.triple.aaa.domain.forecast.daily

interface UpdateDailyForecastUC {
    suspend operator fun invoke(): Result<Unit>
}
