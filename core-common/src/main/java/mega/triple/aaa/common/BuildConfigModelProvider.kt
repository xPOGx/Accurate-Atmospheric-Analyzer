package mega.triple.aaa.common

import mega.triple.aaa.common.model.BuildConfigModel

interface BuildConfigModelProvider {
    fun provide(): BuildConfigModel

    fun populate(
        isDebug: Boolean,
        apiKey: String,
    )
}
