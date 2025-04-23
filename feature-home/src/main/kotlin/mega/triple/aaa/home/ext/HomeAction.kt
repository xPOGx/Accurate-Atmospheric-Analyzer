package mega.triple.aaa.home.ext

sealed class HomeAction {
    object OnNavigateSearch : HomeAction()

    object OnNavigateSettings : HomeAction()

    object UpdateAllData : HomeAction()

    object Refresh : HomeAction()

    data class ChangeDay(
        val index: Int,
    ) : HomeAction()

    data object ChangeEditMode : HomeAction()

    data class OnCardClick(
        val item: HomeCardType,
    ) : HomeAction()

    data object OnAddFirstCardClick : HomeAction()

    data class AddCard(
        val item: HomeCardType,
    ) : HomeAction()

    data class HideCard(
        val item: HomeCardType,
    ) : HomeAction()
}
