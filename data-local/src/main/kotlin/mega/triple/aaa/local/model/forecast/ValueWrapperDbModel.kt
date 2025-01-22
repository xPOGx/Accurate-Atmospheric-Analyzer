package mega.triple.aaa.local.model.forecast

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class ValueWrapperDbModel(
    @Embedded("average")
    val average: ValueDbModel?,
    @Embedded("maximum")
    val maximum: ValueDbModel?,
    @Embedded("minimum")
    val minimum: ValueDbModel?
)
