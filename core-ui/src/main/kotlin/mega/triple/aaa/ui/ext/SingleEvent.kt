package mega.triple.aaa.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import mega.triple.aaa.common.ext.safeLaunch

class SingleEvent {
    private val channel = Channel<Unit>(Channel.BUFFERED)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val lock = Any()
    private val flow = channel.receiveAsFlow()

    fun fire() {
        synchronized(lock) {
            scope.safeLaunch {
                channel.send(Unit)
            }
        }
    }

    @Composable
    fun collectEffect(
        collector: FlowCollector<Unit>,
    ) {
        val flowWithLifecycle = rememberFlowWithLifecycle()
        LaunchedEffect(flowWithLifecycle) {
            flowWithLifecycle.collect(collector)
        }
    }

    @Composable
    private fun rememberFlowWithLifecycle(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        minActionState: Lifecycle.State = Lifecycle.State.STARTED,
    ): Flow<Unit> = remember(flow, lifecycleOwner) {
        flow.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = minActionState,
        )
    }
}
