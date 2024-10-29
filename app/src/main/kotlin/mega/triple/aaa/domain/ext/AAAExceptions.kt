package mega.triple.aaa.domain.ext

sealed class AAAExceptions(
    override val message: String,
    override val cause: Throwable? = null
) : Throwable(message, cause) {
    companion object {
        const val DB_EMPTY = "Database is empty"
        const val LOCATION_KEY_NULL = "Location key is null!"
        const val NULL_RESULT = "Result is null"
    }
}

class EmptyLocationKey : AAAExceptions(LOCATION_KEY_NULL)

class EmptyDatabase : AAAExceptions(DB_EMPTY)

class NullResult : AAAExceptions(NULL_RESULT)
