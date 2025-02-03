package mega.triple.aaa.domain.ext

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mega.triple.aaa.domain.forecast.daily.Get5DayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTodayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTomorrowForecastUC
import mega.triple.aaa.domain.forecast.daily.GetYesterdayForecastUC
import mega.triple.aaa.domain.forecast.daily.UpdateDailyForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toUiModel
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel

class ForecastHelper(
    private val getYesterdayForecastUC: GetYesterdayForecastUC,
    private val getTodayForecastUC: GetTodayForecastUC,
    private val getTomorrowForecastUC: GetTomorrowForecastUC,
    private val get5DayForecastUC: Get5DayForecastUC,
    private val updateDailyForecastUC: UpdateDailyForecastUC,
) {
    private val _forecastFlows = MutableStateFlow<ForecastFlows>(ForecastFlows())
    val forecastFlows = _forecastFlows.asStateFlow()

    suspend fun initFlows() {
        _forecastFlows.update { ForecastFlows(isLoading = true) }

        val yesterday = getYesterdayForecastUC().getOrNull()?.toUiModel()
        val today = getTodayForecastUC().getOrNull()?.toUiModel()
        val tomorrow = getTomorrowForecastUC().getOrNull()?.toUiModel()
        val forecast = get5DayForecastUC().getOrNull()?.map { it.toUiModel() } ?: emptyList()

        _forecastFlows.update {
            ForecastFlows(
                yesterday = yesterday,
                today = today,
                tomorrow = tomorrow,
                forecast = forecast,
                isAllEmpty = today == null,
                isLoading = false,
            )
        }
    }

    suspend fun reinit() {
        _forecastFlows.update { ForecastFlows(isLoading = true) }
        updateDailyForecastUC()
            .onSuccess {
                initFlows()
            }
            .onFailure {
                _forecastFlows.update { ForecastFlows(isAllEmpty = true) }
            }
    }
}

data class ForecastFlows(
    val yesterday: DailyForecastUiModel? = null,
    val today: DailyForecastUiModel? = null,
    val tomorrow: DailyForecastUiModel? = null,
    val forecast: List<DailyForecastUiModel> = emptyList(),
    val isAllEmpty: Boolean = false,
    val isLoading: Boolean = false,
)
