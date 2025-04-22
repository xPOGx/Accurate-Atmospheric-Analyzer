package mega.triple.aaa.common.analytic.impl

interface Analytic {

    fun subscribe(
        action: (String) -> Unit,
    )

    fun logEvent(e: String)
}
