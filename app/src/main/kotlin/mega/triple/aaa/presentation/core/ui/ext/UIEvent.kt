package mega.triple.aaa.presentation.core.ui.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class UIEvent<T> : Flow<T> {
    private val channel = Channel<T>()
    private val scope = CoroutineScope(Dispatchers.Main)
    private val lock = Any()

    fun send(value: T) {
        synchronized(lock) {
            scope.launch {
                channel.send(value)
            }
        }
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        channel.consumeAsFlow().collect(collector)
    }
}