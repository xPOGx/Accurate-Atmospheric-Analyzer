package mega.triple.aaa.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.domain.forecast.daily.UpdateDailyForecastUC
import mega.triple.aaa.domain.location.GetCitiesUC
import mega.triple.aaa.domain.location.GetContinentsUC
import mega.triple.aaa.domain.location.GetCountriesUC
import mega.triple.aaa.domain.location.SetLocationUC
import mega.triple.aaa.domain.location.model.CityDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.location.model.ContinentDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.location.model.CountryDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toDomainModel
import mega.triple.aaa.search.ext.SearchAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.ext.LocationType
import mega.triple.aaa.ui.ext.LocationType.*
import mega.triple.aaa.ui.ext.SingleEvent
import mega.triple.aaa.ui.ext.UI
import mega.triple.aaa.ui.ext.UI.Companion.getOrNull
import mega.triple.aaa.ui.ext.UIEvent
import mega.triple.aaa.ui.ext.UiText
import mega.triple.aaa.ui.ext.asUiText
import mega.triple.aaa.ui.model.location.LocationUiModel

class SearchViewModel(
    private val getContinentsUC: GetContinentsUC,
    private val getCountriesUC: GetCountriesUC,
    private val getCitiesUC: GetCitiesUC,
    private val setLocationUC: SetLocationUC,
    private val updateDailyForecastUC: UpdateDailyForecastUC,
) : ViewModel() {
    // FLOWS
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    // EVENTS
    val onNavigationBack = SingleEvent()
    val onSaveSuccess = SingleEvent()
    val onToast = UIEvent<UiText>()

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.SaveContinent -> saveContinent(action.continentId)
            is SearchAction.SaveCountry -> saveCountry(action.countryId)
            is SearchAction.SaveCity -> saveCity(action.cityId)
            is SearchAction.SaveAll -> saveAll()
            is SearchAction.LoadLocations -> loadList(action.type)
            is SearchAction.OnNavigateBack -> onNavigationBack.fire()
            is SearchAction.ChangeEditMode -> changeEditMode(action.mode)
            is SearchAction.ChangeFilterQuery -> filterLocationList(action.query)
        }
    }

    private fun saveContinent(continentId: String) = viewModelScope.safeLaunch {
        val continents = getContinentsUC()
            .getOrNull()
            ?.map { it.toUiModel() }
        val continent = continents?.find { it.id == continentId }

        _uiState.update { state ->
            state.copy(
                location = LocationUiModel(continent = continent),
                editMode = null,
            )
        }
    }


    private fun saveCountry(countryId: String) = viewModelScope.safeLaunch {
        val continentId = _uiState.value.location.continent?.id ?: return@safeLaunch
        val countries = getCountriesUC(continentId)
            .getOrNull()
            ?.map { it.toUiModel() }
        val country = countries?.find { it.id == countryId }

        _uiState.update { state ->
            state.copy(
                location = state.location.copy(
                    country = country,
                    city = null,
                ),
                editMode = null,
            )
        }
    }

    private fun saveCity(cityId: String) = viewModelScope.safeLaunch {
        val continentId = _uiState.value.location.continent?.id ?: return@safeLaunch
        val countryId = _uiState.value.location.country?.id ?: return@safeLaunch
        val cities = getCitiesUC(continentId, countryId)
            .getOrNull()
            ?.map { it.toUiModel() }
        val city = cities?.find { it.id == cityId }

        _uiState.update { state ->
            state.copy(
                location = state.location.copy(city = city),
                editMode = null,
            )
        }
    }

    private fun loadList(type: LocationType) {
        _uiState.update {
            it.copy(
                locationList = UI.LOADING,
                filteredList = emptyList(),
                editMode = type,
            )
        }
        viewModelScope.safeLaunch {
            when (type) {
                CONTINENT -> {
                    getContinentsUC().mapCatching { domainModels ->
                        domainModels.map { it.toUiModel() }
                    }.onSuccess { uiModels ->
                        val locationList = uiModels
                            .map { it.id to it.englishName }
                            .sortedBy { it.second }
                        _uiState.update { it.copy(locationList = UI.READY(locationList)) }
                    }.onFailure { e ->
                        _uiState.update {
                            it.copy(locationList = UI.ERROR(e.asUiText()) { loadList(type) })
                        }
                    }
                }

                COUNTRY -> {
                    val continentId = _uiState.value.location.continent?.id
                    if (continentId == null) {
                        _uiState.update {
                            it.copy(
                                locationList = UI.ERROR(UiText.Resource(string.search_error_continent))
                            )
                        }
                    } else {
                        getCountriesUC(continentId).mapCatching { domainModels ->
                            domainModels.map { it.toUiModel() }
                        }.onSuccess { uiModels ->
                            val locationList = uiModels
                                .map { it.id to it.englishName }
                                .sortedBy { it.second }
                            _uiState.update { it.copy(locationList = UI.READY(locationList)) }
                        }.onFailure { e ->
                            _uiState.update {
                                it.copy(locationList = UI.ERROR(e.asUiText()) { loadList(type) })
                            }
                        }
                    }
                }

                CITY -> {
                    val continentId = _uiState.value.location.continent?.id
                    val countryId = _uiState.value.location.country?.id
                    if (continentId == null || countryId == null) {
                        val error = when {
                            continentId == null -> UiText.Resource(string.search_error_continent)
                            else -> UiText.Resource(string.search_error_country)
                        }
                        _uiState.update {
                            it.copy(locationList = UI.ERROR(error))
                        }
                    } else {
                        getCitiesUC(continentId, countryId).mapCatching { domainModels ->
                            domainModels.map { it.toUiModel() }
                        }.onSuccess { uiModels ->
                            val locationList = uiModels
                                .map { it.id to "${it.englishName} - ${it.englishType}" }
                                .sortedBy { it.second }
                            _uiState.update { it.copy(locationList = UI.READY(locationList)) }
                        }.onFailure { e ->
                            _uiState.update {
                                it.copy(locationList = UI.ERROR(e.asUiText()) { loadList(type) })
                            }
                        }
                    }
                }
            }
            filterLocationList()
        }
    }

    private fun changeEditMode(mode: LocationType?) {
        _uiState.update {
            it.copy(editMode = mode)
        }
    }

    private fun filterLocationList(query: String = "") {
        if (_uiState.value.locationList is UI.ERROR) return
        val list =  if (query.isBlank()) {
            _uiState.value.locationList.getOrNull().orEmpty()
        } else {
            _uiState.value.locationList
                .getOrNull()
                .orEmpty()
                .filter { it.toString().contains(query, true) }

        }
        _uiState.update {
            it.copy(filteredList = list)
        }
    }

    private fun saveAll() {
        viewModelScope.safeLaunch {
            setLocationUC(_uiState.value.location.toDomainModel())
                .onSuccess {
                    updateDailyForecastUC()
                        .onSuccess {
                            onSaveSuccess.fire()
                        }.onFailure {
                            onToast.send(
                                it.asUiText(UiText.Resource(string.search_error_update))
                            )
                        }
                }
                .onFailure {
                    onToast.send(
                        it.asUiText(UiText.Resource(string.search_error_save))
                    )
                }
        }
    }
}

data class SearchUiState(
    val location: LocationUiModel = LocationUiModel(),
    val locationList: UI<List<Pair<String?, String?>>> = UI.LOADING,
    val editMode: LocationType? = null,
    val filteredList: List<Pair<String?, String?>> = emptyList(),
)
