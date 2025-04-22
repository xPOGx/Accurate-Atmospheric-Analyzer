package mega.triple.aaa.common.analytic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import mega.triple.aaa.common.analytic.impl.Analytic
import mega.triple.aaa.common.ext.safeLaunch

class AAAAnalytic : Analytic {
    private val publisher = MutableSharedFlow<String>()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun subscribe(
        action: (String) -> Unit,
    ) {
        ioScope.safeLaunch {
            publisher.collectLatest {
                action(it)
            }
        }
    }

    override fun logEvent(e: String) {
        ioScope.safeLaunch {
            publisher.emit(e)
        }
    }
}
