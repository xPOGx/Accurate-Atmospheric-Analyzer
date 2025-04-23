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
            is HomeAction.ChangeDay -> setTabId(action.index)
            HomeAction.ChangeEditMode -> toggleEditMode()
            is HomeAction.OnCardClick -> onCardClick(action.item)
            is HomeAction.AddCard -> addCard(action.item)
            is HomeAction.HideCard -> hideCard(action.item)
            HomeAction.OnAddFirstCardClick -> addFirstCard()
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

    private fun setTabId(index: Int) {
        _uiState.update {
            it.copy(selectedTabId = index)
        }
    }

    private fun toggleEditMode() {
        _uiState.update {
            it.copy(
                editMode = !it.editMode,
                uvCustomVisible = false,
                selectedCard = null,
            )
        }
    }

    private fun onCardClick(item: HomeCardType) {
        val uiState = _uiState.value
        if (item == HomeCardType.UV_INDEX && !uiState.editMode) {
            _uiState.update {
                it.copy(uvCustomVisible = !it.uvCustomVisible)
            }
            return
        }
        if (!uiState.editMode) return
        if (item == uiState.selectedCard) {
            _uiState.update {
                it.copy(selectedCard = null)
            }
            return
        }
        if (uiState.selectedCard == null) {
            _uiState.update {
                it.copy(selectedCard = item)
            }
            return
        }
        uiState.selectedCard.let { selected ->
            val temp = mutableMapOf<HomeCardType, Boolean>()
            uiState.cardsWrapper.cards.forEach { entry ->
                when (entry.key) {
                    item -> temp[selected] = uiState.cardsWrapper.cards[selected] ?: return
                    selected -> temp[item] = uiState.cardsWrapper.cards[item] ?: return
                    else -> temp[entry.key] = entry.value
                }
            }
            _uiState.update {
                it.copy(
                    selectedCard = null,
                )
            }
            setCardsSetup(temp)
        }
    }

    private fun addCard(item: HomeCardType) {
        val cards = uiState.value.cardsWrapper.cards.toMutableMap()
        cards[item] = true
        setCardsSetup(cards)
    }

    private fun hideCard(item: HomeCardType) {
        val cards = uiState.value.cardsWrapper.cards.toMutableMap()
        cards[item] = false
        setCardsSetup(cards)
    }

    private fun addFirstCard() {
        val temp = uiState.value.cardsWrapper.cards.toMutableMap()
        val key = temp.keys.first()
        temp[key] = true
        setCardsSetup(temp)
    }
}

data class HomeUiState(
    val location: UI<LocationUiModel?> = UI.LOADING,
    val forecastFlows: ForecastFlows = ForecastFlows(),
    val lastUpdatedDate: Calendar? = null,
    val cardsWrapper: HomeCardWrapper = HomeCardWrapper(),
    val selectedTabId: Int = 0,
    val editMode: Boolean = false,
    val isRefreshing: Boolean = false,
    val uvCustomVisible: Boolean = false,
    val selectedCard: HomeCardType? = null,
)

data class HomeCardWrapper(
    val cards : Map<HomeCardType, Boolean> = emptyMap(),
    val time: Long = System.currentTimeMillis(),
)
