package mega.triple.aaa.presentation.feature.analytic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object AAAAnalytic {
    private val publisher = MutableSharedFlow<String>()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun subscribe(
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        action: (String) -> Unit,
    ) {
        scope.launch {
            publisher.collectLatest {
                action(it)
            }
        }
    }

    fun logEvent(e: String) {
        ioScope.launch {
            publisher.emit(e)
        }
    }
}