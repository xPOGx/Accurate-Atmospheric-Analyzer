package mega.triple.aaa.common.analytic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import mega.triple.aaa.common.ext.safeLaunch

object AAAAnalytic {
    private val publisher = MutableSharedFlow<String>()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun subscribe(
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        action: (String) -> Unit,
    ) {
        scope.safeLaunch {
            publisher.collectLatest {
                action(it)
            }
        }
    }

    fun logEvent(e: String) {
        ioScope.safeLaunch {
            publisher.emit(e)
        }
    }
}
