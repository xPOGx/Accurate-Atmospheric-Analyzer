package mega.triple.aaa.domain.ext

import kotlin.jvm.Throws

const val NULL_MESSAGE = "Value can't be null"
const val DB_EMPTY = "Database is empty"

@Throws(IllegalStateException::class)
fun <T> validateNotNull(value: T?): T = value ?: throw IllegalStateException(NULL_MESSAGE)
