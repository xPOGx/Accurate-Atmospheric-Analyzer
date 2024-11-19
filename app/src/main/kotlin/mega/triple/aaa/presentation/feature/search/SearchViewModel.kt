package mega.triple.aaa.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mega.triple.aaa.domain.location.GetCitiesUC
import mega.triple.aaa.domain.location.GetContinentsUC
import mega.triple.aaa.domain.location.GetCountriesUC
import mega.triple.aaa.domain.location.SetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toDomainModel
import mega.triple.aaa.presentation.core.common.safeLaunch
import mega.triple.aaa.presentation.core.ui.ext.LocationType
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CITY
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CONTINENT
import mega.triple.aaa.presentation.core.ui.ext.LocationType.COUNTRY
import mega.triple.aaa.presentation.core.ui.ext.SingleEvent
import mega.triple.aaa.presentation.core.ui.ext.UI
import mega.triple.aaa.presentation.core.ui.ext.UIEvent
import mega.triple.aaa.presentation.core.ui.model.location.CityUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.location.ContinentUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.location.CountryUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.location.LocationUiModel
import mega.triple.aaa.presentation.feature.search.ext.SearchAction
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getContinentsUC: GetContinentsUC,
    private val getCountriesUC: GetCountriesUC,
    private val getCitiesUC: GetCitiesUC,
    private val setLocationUC: SetLocationUC,
) : ViewModel() {
    // FLOWS
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    // EVENTS
    val onNavigationBack = SingleEvent()
    val onSaveSuccess = SingleEvent()
    val onToast = UIEvent<String>()

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.SaveContinent -> saveContinent(action.continentId)

            is SearchAction.SaveCountry -> saveCountry(action.countryId)

            is SearchAction.SaveCity -> saveCity(action.cityId)

            is SearchAction.SaveAll -> saveAll()

            is SearchAction.LoadLocations -> loadList(action.type)

            is SearchAction.OnNavigateBack -> onNavigationBack.fire()
        }
    }

    private fun saveContinent(continentId: String) = viewModelScope.safeLaunch {
        val continents = getContinentsUC()
            .getOrNull()
            ?.map { it.toUiModel() }
        val continent = continents?.find { it.id == continentId }

        _uiState.update { state ->
            state.copy(location = LocationUiModel(continent = continent))
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
            state.copy(location = state.location.copy(city = city))
        }
    }

    private fun loadList(type: LocationType) {
        _uiState.update { it.copy(locationList = UI.LOADING) }
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
                            it.copy(locationList = UI.ERROR(e) { loadList(type) })
                        }
                    }
                }

                COUNTRY -> {
                    val continentId = _uiState.value.location.continent?.id
                    if (continentId == null) {
                        _uiState.update {
                            it.copy(locationList = UI.ERROR(Exception("Continent not chosen")))
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
                                it.copy(locationList = UI.ERROR(e) { loadList(type) })
                            }
                        }
                    }
                }

                CITY -> {
                    val continentId = _uiState.value.location.continent?.id
                    val countryId = _uiState.value.location.country?.id
                    if (continentId == null || countryId == null) {
                        val error = when {
                            continentId == null -> "Continent not chosen"
                            else -> "Country not chosen"
                        }
                        _uiState.update {
                            it.copy(locationList = UI.ERROR(Exception(error)))
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
                                it.copy(locationList = UI.ERROR(e) { loadList(type) })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveAll() {
        viewModelScope.safeLaunch {
            setLocationUC(_uiState.value.location.toDomainModel())
                .onSuccess { onSaveSuccess.fire() }
                .onFailure { onToast.send(it.message ?: "Error saving location") }
        }
    }
}

data class SearchUiState(
    val location: LocationUiModel = LocationUiModel(),
    val locationList: UI<List<Pair<String?, String?>>> = UI.LOADING,
)
