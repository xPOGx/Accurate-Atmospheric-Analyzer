package mega.triple.aaa.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mega.triple.aaa.presentation.core.ui.ext.LocationType
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CITY
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CONTINENT
import mega.triple.aaa.presentation.core.ui.ext.LocationType.COUNTRY
import mega.triple.aaa.presentation.core.ui.model.LocationUiModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUiState(uiState: SearchUiState) = _uiState.update { uiState }

    fun loadList(type: LocationType) {
        viewModelScope.launch {
            when (type) {
                CONTINENT -> _uiState.update {
                    it.copy(locationList = listOf("A", "B", "C"))
                }

                COUNTRY -> _uiState.update {
                    it.copy(locationList = listOf("AB", "BC", "CD"))
                }

                CITY -> _uiState.update {
                    it.copy(locationList = listOf("ABC", "BCD", "CDE"))
                }
            }
        }
    }
}

data class SearchUiState(
    val locationUiModel: LocationUiModel = LocationUiModel(),
    val locationList: List<String> = emptyList(),
)
