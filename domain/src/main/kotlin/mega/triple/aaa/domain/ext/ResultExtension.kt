package mega.triple.aaa.domain.ext

suspend fun <T> resultLauncher(
    body: suspend () -> T,
) = try {
    Result.success(body())
} catch (e: Throwable) {
    Result.failure(e)
}
