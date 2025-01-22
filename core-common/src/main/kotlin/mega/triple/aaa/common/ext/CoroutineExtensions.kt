package mega.triple.aaa.common.ext

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.safeLaunch(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    coroutineError: ((Throwable) -> Unit)? = null,
    blockError: (suspend CoroutineScope.(Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit,
): Job = this.launch(
    context = dispatcher + CoroutineExceptionHandler { _, throwable ->
        coroutineError?.invoke(throwable)
        Firebase.crashlytics.log(throwable.message.toString())
    },
) {
    try {
        block()
    } catch (e: Exception) {
        blockError?.invoke(this, e)
    }
}
