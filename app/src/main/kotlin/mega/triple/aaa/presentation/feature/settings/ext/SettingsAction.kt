package mega.triple.aaa.presentation.feature.settings.ext

import mega.triple.aaa.ui.model.ThemeTypeUiModel

sealed class SettingsAction {
    data object OnNavigateBack : SettingsAction()
    class OnThemeChange(val theme: ThemeTypeUiModel) : SettingsAction()
}