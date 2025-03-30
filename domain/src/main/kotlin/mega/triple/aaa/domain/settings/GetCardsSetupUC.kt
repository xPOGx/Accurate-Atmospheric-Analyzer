package mega.triple.aaa.domain.settings

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel

fun interface GetCardsSetupUC {
    operator fun invoke(): Flow<Map<HomeCardTypeDomainModel, Boolean>>
}
