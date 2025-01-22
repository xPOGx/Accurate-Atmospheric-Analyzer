package mega.triple.aaa.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.location.model.LocationDomainModel.Companion.toUiModel
import mega.triple.aaa.preference.SettingsDatastore
import mega.triple.aaa.presentation.core.ui.ext.UI
import mega.triple.aaa.presentation.core.ui.model.location.LocationUiModel
import mega.triple.aaa.presentation.feature.settings.ext.ThemeType
import mega.triple.aaa.presentation.feature.settings.ext.ThemeType.Companion.toThemeType
import javax.inject.Inject

@HiltViewModel
class AAAViewModel @Inject constructor(
    private val getLocationUC: GetLocationUC,
    settingsDatastore: SettingsDatastore,
) : ViewModel() {
    // FLOWS
    private val _location: MutableStateFlow<UI<LocationUiModel?>> = MutableStateFlow(UI.LOADING)
    val location = _location.asStateFlow()

    val themeType: Flow<ThemeType> = settingsDatastore.getThemeType().map { it.toThemeType() }

    init {
        subscribeLocation()
    }

    private fun subscribeLocation() = viewModelScope.safeLaunch {
        getLocationUC().collectLatest { location ->
            _location.update { UI.READY(location?.toUiModel()) }
        }
    }
}
