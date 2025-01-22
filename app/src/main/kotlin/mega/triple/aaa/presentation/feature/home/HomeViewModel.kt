package mega.triple.aaa.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.domain.ext.ForecastFlows
import mega.triple.aaa.domain.ext.ForecastHelper
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toUiModel
import mega.triple.aaa.ui.ext.UI
import mega.triple.aaa.ui.model.location.LocationUiModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationUC: GetLocationUC,
    private val forecastHelper: ForecastHelper,
) : ViewModel() {
    // FLOWS
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        subscribeLocation()
        subscribeForecast()
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
}

data class HomeUiState(
    val location: UI<LocationUiModel?> = UI.LOADING,
    val forecastFlows: ForecastFlows = ForecastFlows(),
)
