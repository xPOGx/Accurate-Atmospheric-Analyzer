package mega.triple.aaa.presentation.feature.settings.ext

sealed class SettingsAction {
    data object OnNavigateBack : SettingsAction()
    class OnThemeChange(val theme: ThemeType) : SettingsAction()
}