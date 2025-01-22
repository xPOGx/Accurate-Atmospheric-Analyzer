package mega.triple.aaa.domain.location

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.domain.location.model.LocationDomainModel

interface GetLocationUC {
    operator fun invoke(): Flow<LocationDomainModel?>
}
