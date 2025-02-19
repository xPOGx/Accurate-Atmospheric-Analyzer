package mega.triple.aaa.domain.settings.impl

import mega.triple.aaa.domain.settings.SetLastUpdateUC
import mega.triple.aaa.preference.SettingsDatastore

class SetLastUpdateUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : SetLastUpdateUC {
    override suspend operator fun invoke(date: Long): Result<Unit> {
        return try {
            settingsDatastore.setLastUpdate(date)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
