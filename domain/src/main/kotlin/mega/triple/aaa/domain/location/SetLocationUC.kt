package mega.triple.aaa.domain.location

import mega.triple.aaa.domain.location.model.LocationDomainModel

interface SetLocationUC {
    suspend operator fun invoke(domainModel: LocationDomainModel): Result<Unit>
}
