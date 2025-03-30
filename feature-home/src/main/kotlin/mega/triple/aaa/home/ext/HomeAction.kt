package mega.triple.aaa.home.ext

sealed class HomeAction {
    object OnNavigateSearch : HomeAction()
    object OnNavigateSettings : HomeAction()
    object UpdateAllData : HomeAction()
    object Refresh : HomeAction()
    data class UpdateCardsSetup(
        val cards: Map<HomeCardType, Boolean>,
    ) : HomeAction()
}
