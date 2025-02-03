package mega.triple.aaa.search.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SearchDirection {
    @Serializable
    data object Search : SearchDirection()
}
