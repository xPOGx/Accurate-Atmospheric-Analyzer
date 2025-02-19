package mega.triple.aaa.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.settings.GetThemeUC
import mega.triple.aaa.domain.settings.model.ThemeTypeDomainModel.Companion.toUiModel
import mega.triple.aaa.ui.ext.UI
import mega.triple.aaa.ui.model.ThemeTypeUiModel
import mega.triple.aaa.ui.model.location.LocationUiModel

class AAAViewModel(
    private val getLocationUC: GetLocationUC,
    getThemeUC: GetThemeUC,
) : ViewModel() {
    // FLOWS
    private val _location: MutableStateFlow<UI<LocationUiModel?>> = MutableStateFlow(UI.LOADING)
    val location = _location.asStateFlow()

    val themeType: Flow<ThemeTypeUiModel> = getThemeUC().map { it.toUiModel() }

    init {
        subscribeLocation()
    }

    private fun subscribeLocation() = viewModelScope.safeLaunch {
        getLocationUC().collectLatest { location ->
            _location.update { UI.READY(location?.toUiModel()) }
        }
    }
}
