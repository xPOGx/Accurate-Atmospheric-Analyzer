package mega.triple.aaa.common.impl

import mega.triple.aaa.common.BuildConfigModelProvider
import mega.triple.aaa.common.model.BuildConfigModel

class BuildConfigModelProviderImpl : BuildConfigModelProvider {
    private lateinit var model: BuildConfigModel

    override fun provide(): BuildConfigModel {
        return model
    }

    override fun populate(
        isDebug: Boolean,
        apiKey: String,
    ) {
        model = BuildConfigModel(
            debug = isDebug,
            apiKey = apiKey,
        )
    }
}
