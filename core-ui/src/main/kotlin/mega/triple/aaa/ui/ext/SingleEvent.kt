package mega.triple.aaa.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import mega.triple.aaa.common.ext.safeLaunch

class SingleEvent {
    private val events = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val lock = Any()

    fun fire() {
        synchronized(lock) {
            scope.safeLaunch {
                events.emit(Unit)
            }
        }
    }

    @Composable
    fun collectEffect(collector: FlowCollector<Unit>) {
        LaunchedEffect(this) {
            events.collect(collector)
        }
    }
}
