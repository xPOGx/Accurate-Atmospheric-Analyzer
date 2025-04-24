package mega.triple.aaa.widget.ext

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@SuppressLint("RestrictedApi")
val defaultTextStyle = TextStyle(
    color = ColorProvider(Color.White),
)

private const val CELL_WIDTH = 73
private const val CELL_HEIGHT = 118
private const val CELL_PADDING = 16

/**
 * Cell creation only in Portrait mode
 *
 * Available size in portrait mode
 * (73n - 16) x (118m - 16)
 *
 * @param n X axis >>>
 * @param m Y axis vvv
 *
 * References:
 * - [developer.android.com](https://developer.android.com/develop/ui/views/appwidgets/layouts#:~:text=n%20x%20m,x%20(66m%20%2D%2015))
 */
fun createCell(n: Int, m: Int): DpSize = DpSize(
    (CELL_WIDTH * n - CELL_PADDING).dp,
    (CELL_HEIGHT * m - CELL_PADDING).dp,
)

enum class CellInfo(val info: String) {
    CELL_1_1("1_1"),
    CELL_1_2("1_2"),
    CELL_2_1("2_1"),
    CELL_2_2("2_2"),
}

fun DpSize.cellInfo(): CellInfo =
    CellInfo.entries.firstOrNull {
        it.info == "${this.width.getWidth()}_${this.height.getHeight()}"
    } ?: CellInfo.CELL_2_2

fun DpSize.cellValues(): String = "${this.width.value}_${this.height.value}"

fun Dp.getWidth(): Int = ((this.value + CELL_PADDING) / CELL_WIDTH).toInt()

fun Dp.getHeight(): Int = ((this.value + CELL_PADDING) / CELL_HEIGHT).toInt()