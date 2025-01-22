package mega.triple.aaa.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel.Companion.toUiModel
import mega.triple.aaa.domain.pref.theme.GetThemeUC
import mega.triple.aaa.domain.pref.theme.SetThemeUC
import mega.triple.aaa.settings.ext.SettingsAction
import mega.triple.aaa.ui.ext.SingleEvent
import mega.triple.aaa.ui.model.ThemeTypeUiModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUC: GetThemeUC,
    private val setThemeUC: SetThemeUC,
) : ViewModel() {
    // FLOWS
    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    // EVENTS
    val onNavigationBack = SingleEvent()

    init {
        subscribeThemeType()
    }

    fun onAction(action: SettingsAction) = when (action) {
        SettingsAction.OnNavigateBack -> onNavigationBack.fire()
        is SettingsAction.OnThemeChange -> {
            viewModelScope.safeLaunch {
                setThemeUC(action.theme.toDomainModel())
            }
        }
    }

    private fun subscribeThemeType() = viewModelScope.safeLaunch {
        getThemeUC().collectLatest { type ->
            _uiState.update { it.copy(themeTypeUiModel = type.toUiModel()) }
        }
    }
}

data class SettingsUiState(
    val themeTypeUiModel: ThemeTypeUiModel = ThemeTypeUiModel.LIGHT,
)
