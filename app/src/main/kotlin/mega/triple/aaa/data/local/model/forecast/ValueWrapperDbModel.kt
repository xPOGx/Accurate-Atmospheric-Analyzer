package mega.triple.aaa.data.local.model.forecast

import androidx.room.Embedded

data class ValueWrapperDbModel(
    @Embedded("average")
    val average: ValueDbModel?,
    @Embedded("maximum")
    val maximum: ValueDbModel?,
    @Embedded("minimum")
    val minimum: ValueDbModel?
)
