package mega.triple.aaa.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.domain.ext.ForecastFlows
import mega.triple.aaa.domain.ext.ForecastHelper
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.settings.GetLastUpdateUC
import mega.triple.aaa.home.ext.HomeAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.ext.SingleEvent
import mega.triple.aaa.ui.ext.UI
import mega.triple.aaa.ui.ext.UIEvent
import mega.triple.aaa.ui.ext.UiText
import mega.triple.aaa.ui.model.location.LocationUiModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val getLocationUC: GetLocationUC,
    private val getLastUpdateUC: GetLastUpdateUC,
    private val forecastHelper: ForecastHelper,
) : ViewModel() {
    // FLOWS
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    // Events
    val onNavigateToSettings = SingleEvent()
    val onNavigateToSearch = SingleEvent()
    val onToast = UIEvent<UiText>()

    init {
        subscribeLocation()
        subscribeForecast()
        subscribeLastUpdate()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnNavigateSearch -> onNavigateToSearch.fire()
            HomeAction.OnNavigateSettings -> onNavigateToSettings.fire()
            HomeAction.UpdateAllData -> updateAll()
            HomeAction.Refresh -> refresh()
        }
    }

    private fun updateAll() = viewModelScope.safeLaunch {
        forecastHelper.reinit()
    }

    private fun refresh() = viewModelScope.safeLaunch {
        _uiState.update {
            it.copy(isRefreshing = true)
        }
        val now = System.currentTimeMillis()
        val lastUpdate = getLastUpdateUC().firstOrNull() ?: 0L
        val oneDay = TimeUnit.DAYS.toMillis(1)
        if (now - lastUpdate > oneDay) {
            forecastHelper.reinit()
            onToast.send(UiText.Resource(string.pull_to_refresh_updated))
        } else {
            delay(500)
            onToast.send(UiText.Resource(string.pull_to_refresh_up_to_date))
        }
        _uiState.update {
            it.copy(isRefreshing = false)
        }
    }

    private fun subscribeLocation() = viewModelScope.safeLaunch {
        getLocationUC().collectLatest { location ->
            _uiState.update {
                it.copy(location = UI.READY(location?.toUiModel()))
            }
            location?.let {
                forecastHelper.initFlows()
            }
        }
    }

    private fun subscribeForecast() = viewModelScope.safeLaunch {
        forecastHelper.forecastFlows.collectLatest { flows ->
            _uiState.update {
                it.copy(forecastFlows = flows)
            }
        }
    }

    private fun subscribeLastUpdate() = viewModelScope.safeLaunch {
        getLastUpdateUC().collectLatest { date ->
            _uiState.update {
                it.copy(
                    lastUpdatedDate = date?.let {
                        Calendar.getInstance().apply { timeInMillis = it }
                    }
                )
            }
        }
    }
}

data class HomeUiState(
    val location: UI<LocationUiModel?> = UI.LOADING,
    val forecastFlows: ForecastFlows = ForecastFlows(),
    val lastUpdatedDate: Calendar? = null,
    val isRefreshing: Boolean = false,
)
