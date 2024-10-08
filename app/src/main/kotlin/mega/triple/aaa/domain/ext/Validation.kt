package mega.triple.aaa.domain.ext

import kotlin.jvm.Throws

const val NULL_MESSAGE = "Value can't be null"

@Throws(IllegalStateException::class)
fun <T> validateNotNull(value: T?): T = value ?: throw IllegalStateException(NULL_MESSAGE)
