package mega.triple.aaa.home.ext

object MapExtension {
    fun <K> Map<K, Boolean>.partition(): Pair<List<K>, List<K>> {
        val trueList = mutableListOf<K>()
        val falseList = mutableListOf<K>()

        forEach { entry ->
            if (entry.value) {
                trueList.add(entry.key)
            } else {
                falseList.add(entry.key)
            }
        }

        return trueList to falseList
    }
}
