package mega.triple.aaa.domain.settings

import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel

fun interface SetCardsSetupUC {
    suspend operator fun invoke(domainModels: Map<HomeCardTypeDomainModel, Boolean>): Result<Unit>
}
