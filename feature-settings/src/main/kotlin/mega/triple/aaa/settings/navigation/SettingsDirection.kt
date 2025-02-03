package mega.triple.aaa.settings.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SettingsDirection {
    @Serializable
    data object Settings : SettingsDirection()
}
