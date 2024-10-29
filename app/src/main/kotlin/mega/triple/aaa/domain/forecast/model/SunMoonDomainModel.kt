package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.SunMoonDbModel
import mega.triple.aaa.data.network.response.model.SunMoonResponse

data class SunMoonDomainModel(
    val age: Int?,
    val epochRise: Long?,
    val epochSet: Long?,
    val phase: String?,
    val timeRise: String?,
    val timeSet: String?
) {
    companion object {
        fun SunMoonResponse.toDbModel(): SunMoonDbModel =
            SunMoonDbModel(
                age = age,
                epochRise = epochRise,
                epochSet = epochSet,
                phase = phase,
                timeRise = timeRise,
                timeSet = timeSet,
            )

        fun SunMoonResponse.toDomainModel(): SunMoonDomainModel =
            SunMoonDomainModel(
                age = age,
                epochRise = epochRise,
                epochSet = epochSet,
                phase = phase,
                timeRise = timeRise,
                timeSet = timeSet,
            )

        fun SunMoonDbModel.toDomainModel(): SunMoonDomainModel =
            SunMoonDomainModel(
                age = age,
                epochRise = epochRise,
                epochSet = epochSet,
                phase = phase,
                timeRise = timeRise,
                timeSet = timeSet,
            )
    }
}
