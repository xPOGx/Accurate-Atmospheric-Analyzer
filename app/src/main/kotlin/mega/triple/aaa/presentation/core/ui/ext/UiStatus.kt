package mega.triple.aaa.presentation.core.ui.ext

sealed class UiStatus {
    data object LOADING : UiStatus()
    data object DONE : UiStatus()
    class ERROR(error: String?, val onTryAgain: (() -> Unit)? = null) : UiStatus() {
        val error: String = error ?: "Unknown error"
    }
}
