package mega.triple.aaa.domain.settings

import kotlinx.coroutines.flow.Flow

interface GetLastUpdateUC {
    operator fun invoke(): Flow<Long?>
}
