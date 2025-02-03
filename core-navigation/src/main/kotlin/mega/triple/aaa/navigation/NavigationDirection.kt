package mega.triple.aaa.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationDirection {

    @Serializable
    data object Home : NavigationDirection()

    @Serializable
    data object Search : NavigationDirection()

    @Serializable
    data object Settings : NavigationDirection()
}
