package mega.triple.aaa.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mega.triple.aaa.domain.location.GetCitiesUseCase
import mega.triple.aaa.domain.location.GetContinentsUseCase
import mega.triple.aaa.domain.location.GetCountriesUseCase
import mega.triple.aaa.presentation.core.ui.ext.LocationType
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CITY
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CONTINENT
import mega.triple.aaa.presentation.core.ui.ext.LocationType.COUNTRY
import mega.triple.aaa.presentation.core.ui.model.CityUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.ContinentUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.CountryUiModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.LocationWrapper
import mega.triple.aaa.presentation.feature.search.ext.SearchAction
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getContinentsUseCase: GetContinentsUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun loadList(type: LocationType) {
        viewModelScope.launch {
            when (type) {
                CONTINENT -> {
                    getContinentsUseCase().mapCatching { domainModels ->
                        domainModels.map { it.toUiModel() }
                    }.onSuccess { uiModels ->
                        val locationList = uiModels.map {
                            it.id to it.englishName
                        }
                        _uiState.update {
                            it.copy(locationList = locationList)
                        }
                    }.onFailure {
                        // TODO uiEvent error
                        val t = it
                    }
                }

                COUNTRY -> {
                    val continentId = _uiState.value.location.continent?.id ?: return@launch
                    getCountriesUseCase(continentId).mapCatching { domainModels ->
                        domainModels.map { it.toUiModel() }
                    }.onSuccess { uiModels ->
                        val locationList = uiModels.map {
                            it.id to it.englishName
                        }
                        _uiState.update {
                            it.copy(locationList = locationList)
                        }
                    }.onFailure {
                        // TODO uiEvent error
                        val t = it
                    }
                }

                CITY -> {
                    val continentId = _uiState.value.location.continent?.id ?: return@launch
                    val countryId = _uiState.value.location.country?.id ?: return@launch
                    getCitiesUseCase(continentId, countryId).mapCatching { domainModels ->
                        domainModels.map { it.toUiModel() }
                    }.onSuccess { uiModels ->
                        val locationList = uiModels.map {
                            it.id to it.englishName
                        }
                        _uiState.update {
                            it.copy(locationList = locationList)
                        }
                    }.onFailure {
                        // TODO uiEvent error
                        val t = it
                    }
                }
            }
        }
    }

    fun onAction(action: SearchAction) {
        viewModelScope.launch {
            when (action) {
                is SearchAction.Continent -> {
                    val continents = getContinentsUseCase()
                        .getOrNull()
                        ?.map { it.toUiModel() }
                    val continent = continents?.find { it.id == action.continentId }

                    _uiState.update { state ->
                        state.copy(
                            locationList = emptyList(),
                            location = LocationWrapper(continent = continent),
                        )
                    }
                }

                is SearchAction.Country -> {
                    val continentId = _uiState.value.location.continent?.id ?: return@launch
                    val countries = getCountriesUseCase(continentId)
                        .getOrNull()
                        ?.map { it.toUiModel() }
                    val country = countries?.find { it.id == action.countryId }

                    _uiState.update { state ->
                        state.copy(
                            locationList = emptyList(),
                            location = state.location.copy(
                                country = country,
                                city = null,
                            )
                        )
                    }
                }

                is SearchAction.City -> {
                    val continentId = _uiState.value.location.continent?.id ?: return@launch
                    val countryId = _uiState.value.location.country?.id ?: return@launch
                    val cities = getCitiesUseCase(continentId, countryId)
                        .getOrNull()
                        ?.map { it.toUiModel() }
                    val city = cities?.find { it.id == action.cityId }

                    _uiState.update { state ->
                        state.copy(
                            locationList = emptyList(),
                            location = state.location.copy(
                                city = city,
                            )
                        )
                    }
                }
            }
        }
    }
}

data class SearchUiState(
    val location: LocationWrapper = LocationWrapper(),
    val locationList: List<Pair<String, String?>> = emptyList(),
)
