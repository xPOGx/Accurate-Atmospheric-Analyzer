package mega.triple.aaa.presentation.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SingleEvent : Flow<Unit> {
    private val channel = Channel<Unit>(Channel.BUFFERED)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val lock = Any()

    fun fire() {
        synchronized(lock) {
            scope.launch {
                channel.send(Unit)
            }
        }
    }

    override suspend fun collect(collector: FlowCollector<Unit>) {
        channel.consumeAsFlow().collect(collector)
    }

    @Composable
    fun collectEffect(collector: FlowCollector<Unit>) {
        LaunchedEffect(this) {
            collect(collector)
        }
    }
}
