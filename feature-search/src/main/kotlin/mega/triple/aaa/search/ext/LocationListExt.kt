package mega.triple.aaa.search.ext

import mega.triple.aaa.common.ext.Constants

fun List<Pair<String?, String?>>?.getFirstUniqueSeenCharIndex(): Map<Char, Int> {
    val firstLetterIndexes = mutableMapOf<Char, Int>()
    this
        .orEmpty()
        .mapNotNull { it.second?.lowercase()?.firstOrNull() }
        .forEachIndexed { index, char ->
            if (!firstLetterIndexes.contains(char)) {
                firstLetterIndexes[char] = index
            }
        }
    return firstLetterIndexes.toMap()
}

fun Float.getIndexOfCharBasedOnYPosition(
    alphabetHeightInPixels: Float,
): Char {
    var index = ((this) / alphabetHeightInPixels).toInt()
    index = when {
        index > 25 -> 25
        index < 0 -> 0
        else -> index
    }
    return Constants.ALPHABET[index]
}
