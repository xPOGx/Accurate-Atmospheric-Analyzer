package mega.triple.aaa.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mega.triple.aaa.domain.location.GetLocationUseCase
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.ext.UI
import mega.triple.aaa.presentation.core.ui.model.LocationUiModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
) : ViewModel() {
    // FLOWS
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getLocationUseCase().collectLatest { location ->
                delay(1000) // Time simulation
                _uiState.update {
                    it.copy(location = UI.READY(location?.toUiModel()))
                }
            }
        }
    }
}

data class HomeUiState(
    val location: UI<LocationUiModel?> = UI.LOADING,
)
