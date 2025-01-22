package mega.triple.aaa.domain.location

import mega.triple.aaa.domain.location.model.ContinentDomainModel

interface GetContinentsUC {
    suspend operator fun invoke(): Result<List<ContinentDomainModel>>
}
