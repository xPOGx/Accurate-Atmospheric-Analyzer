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

class UIEvent<T> : Flow<T> {
    private val channel = Channel<T>(Channel.BUFFERED)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val lock = Any()

    fun send(value: T) {
        synchronized(lock) {
            scope.launch {
                channel.trySend(value)
            }
        }
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        channel.consumeAsFlow().collect(collector)
    }

    @Composable
    fun collectEffect(collector: FlowCollector<T>) {
        LaunchedEffect(this) {
            collect(collector)
        }
    }
}