package mega.triple.aaa.home.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeDirection {
    @Serializable
    data object Home : HomeDirection()
}
