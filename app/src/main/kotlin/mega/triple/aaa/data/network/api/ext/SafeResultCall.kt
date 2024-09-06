package mega.triple.aaa.data.network.api.ext

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.UnknownHostException

suspend inline fun <reified T> safeResultCall(
    crossinline call: suspend () -> HttpResponse,
): Result<T> = withContext(Dispatchers.IO) {
    try {
        Result.success(call().body())
    } catch (e: Exception) {
        Result.failure(handleException(e))
    }
}

fun handleException(e: Exception): Exception {
    return when (e) {
        is RedirectResponseException -> RedirectError(e.message)
        is ClientRequestException -> ClientError(e.message)
        is ServerResponseException -> ServerError(e.message)
        is ConnectException, is UnknownHostException -> NoInternet(e.message)

        else -> UnknownException(e.message)
    }
}
