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
import mega.triple.aaa.domain.settings.GetCardsSetupUC
import mega.triple.aaa.domain.settings.GetLastUpdateUC
import mega.triple.aaa.domain.settings.SetCardsSetupUC
import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel
import mega.triple.aaa.home.ext.HomeAction
import mega.triple.aaa.home.ext.HomeCardType
import mega.triple.aaa.home.ext.HomeCardType.Companion.toDomainModel
import mega.triple.aaa.home.ext.HomeCardType.Companion.toUiModel
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.ext.SingleEvent
import mega.triple.aaa.ui.ext.UI
import mega.triple.aaa.ui.ext.UIEvent
import mega.triple.aaa.ui.ext.UiText
import mega.triple.aaa.ui.model.location.LocationUiModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val getLocation: GetLocationUC,
    private val getLastUpdate: GetLastUpdateUC,
    private val getCardsSetup: GetCardsSetupUC,
    private val setCardsSetup: SetCardsSetupUC,
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
        subscribeCardsSetup()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnNavigateSearch -> onNavigateToSearch.fire()
            HomeAction.OnNavigateSettings -> onNavigateToSettings.fire()
            HomeAction.UpdateAllData -> updateAll()
            HomeAction.Refresh -> refresh()
            is HomeAction.UpdateCardsSetup -> setCardsSetup(action.cards)
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
        val lastUpdate = getLastUpdate().firstOrNull() ?: 0L
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
        getLocation().collectLatest { location ->
            val model = UI.READY(location?.toUiModel())
            _uiState.update {
                it.copy(location = model)
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
        getLastUpdate().collectLatest { date ->
            val calendar = date?.let {
                Calendar.getInstance().apply { timeInMillis = it }
            }
            _uiState.update {
                it.copy(lastUpdatedDate = calendar)
            }
        }
    }

    private fun subscribeCardsSetup() = viewModelScope.safeLaunch {
        getCardsSetup().collectLatest { cards ->
            if (cards.isEmpty()) {
                val defaultCards = HomeCardTypeDomainModel.entries.associateWith { true }
                setCardsSetup(defaultCards)
            } else {
                val cardsMap = cards.mapKeys { it.key.toUiModel() }
                _uiState.update {
                    it.copy(cardsWrapper = HomeCardWrapper(cardsMap))
                }
            }
        }
    }

    private fun setCardsSetup(cards: Map<HomeCardType, Boolean>) = viewModelScope.safeLaunch {
        val models = cards.mapKeys { it.key.toDomainModel() }
        setCardsSetup(models)
    }
}

data class HomeUiState(
    val location: UI<LocationUiModel?> = UI.LOADING,
    val forecastFlows: ForecastFlows = ForecastFlows(),
    val lastUpdatedDate: Calendar? = null,
    val isRefreshing: Boolean = false,
    val cardsWrapper: HomeCardWrapper = HomeCardWrapper(),
)

data class HomeCardWrapper(
    val cards : Map<HomeCardType, Boolean> = emptyMap(),
    val time: Long = System.currentTimeMillis(),
)
