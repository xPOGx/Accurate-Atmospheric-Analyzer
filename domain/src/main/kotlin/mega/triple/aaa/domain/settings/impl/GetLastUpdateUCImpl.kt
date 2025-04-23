package mega.triple.aaa.domain.settings.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mega.triple.aaa.domain.settings.GetLastUpdateUC
import mega.triple.aaa.preference.SettingsDatastore

class GetLastUpdateUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : GetLastUpdateUC {
    override operator fun invoke(): Flow<Long?> {
        return try {
            settingsDatastore.getLastUpdate()
        } catch (_: Throwable) {
            flowOf(null)
        }
    }
}
