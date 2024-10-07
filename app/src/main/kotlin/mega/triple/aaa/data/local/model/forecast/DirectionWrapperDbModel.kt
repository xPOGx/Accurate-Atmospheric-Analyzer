package mega.triple.aaa.data.local.model.forecast

import androidx.room.Embedded

data class DirectionWrapperDbModel(
    @Embedded("direction")
    val direction: DirectionDbModel?,
    @Embedded("speed")
    val speed: ValueDbModel?
)
