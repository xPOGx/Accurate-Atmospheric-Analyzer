package mega.triple.aaa.home.ext

sealed class HomeAction {
    object OnNavigateSearch : HomeAction()
    object OnNavigateSettings : HomeAction()
}
