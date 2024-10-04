package mega.triple.aaa.presentation.core.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import kotlin.math.cos
import kotlin.math.sin

class DottedCircleProgressBar(context: Context) : View(context) {
    private val paint = Paint()
    private var progress = 0f

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    fun setup(
        progress: Float = this.progress,
    ) {
        this.progress = progress
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Center point
        val centerX = width / 2f
        val centerY = height / 2f

        // Background
        paint.color = Color.TRANSPARENT
        canvas.drawCircle(centerX, centerY, CIRCLE_RADIUS, paint)

        // Draw the dots
        val progressDots = progress * DOT_COUNT / 10
        for (i in 0 until DOT_COUNT) {
            val angle = i * ANGLE_STEP + ANGLE
            if (i < progressDots) {
                paint.color = getColor(progress)
                canvas.drawDot(angle, DotType.BIG)
                canvas.drawDot(angle, DotType.MEDIUM)
                canvas.drawDot(angle, DotType.SMALL)
            } else {
                paint.color = Color.WHITE
                canvas.drawDot(angle, DotType.BIG)
                canvas.drawDot(angle, DotType.MEDIUM)
                canvas.drawDot(angle, DotType.SMALL)
            }
        }

        paint.color = Color.BLACK
        paint.textSize = TEXT_SIZE
        @SuppressLint("DrawAllocation")
        val textBounds = Rect()
        val text = progress.toString()
        paint.getTextBounds(text, 0, text.length, textBounds)
        val textX = centerX - textBounds.width() / 2f
        val textY = centerY + textBounds.height() / 2f
        canvas.drawText(text, textX, textY, paint)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getColor(progress: Float): Int {
        return androidx.compose.ui.graphics.Color(
            when (progress) {
                in 1f.rangeUntil(3f) -> 0xFF8DC443
                in 3f.rangeUntil(6f) -> 0xFFFDD835
                in 6f.rangeUntil(8f) -> 0xFFFFB301
                in 8f.rangeUntil(10f) -> 0xFFD1394A
                else -> if (progress < 1) 0xFF888888 else 0xFF954F71
            }
        ).toArgb()
    }

    private fun Canvas.drawDot(angle: Double, type: DotType) {
        when (type) {
            DotType.BIG -> {
                drawCircle(
                    xPos(angle),
                    yPos(angle),
                    DOT_RADIUS,
                    paint
                )
            }

            DotType.MEDIUM -> {
                drawCircle(
                    xPos(angle, MEDIUM_STEP),
                    yPos(angle, MEDIUM_STEP),
                    DOT_RADIUS_SMALL,
                    paint
                )
            }

            DotType.SMALL -> {
                drawCircle(
                    xPos(angle, SMALL_STEP),
                    yPos(angle, SMALL_STEP),
                    DOT_RADIUS_MEDIUM,
                    paint
                )
            }
        }
    }

    private fun xPos(
        angle: Double,
        multiplier: Float = 0f,
    ) = width / 2f + (CIRCLE_RADIUS + multiplier) * cos(Math.toRadians(angle)).toFloat()

    private fun yPos(
        angle: Double,
        multiplier: Float = 0f,
    ) = height / 2f + (CIRCLE_RADIUS + multiplier) * sin(Math.toRadians(angle)).toFloat()

    companion object {
        private const val CIRCLE_RADIUS = 80f

        private const val DOT_COUNT = 360 / 10
        private const val DOT_RADIUS = CIRCLE_RADIUS / 15
        private const val DOT_RADIUS_MEDIUM = DOT_RADIUS * 0.9f
        private const val DOT_RADIUS_SMALL = DOT_RADIUS * 0.8f

        private const val ANGLE = -90.0
        private const val ANGLE_STEP = 360f / DOT_COUNT

        private const val SMALL_STEP = 20f
        private const val MEDIUM_STEP = 40f

        private const val TEXT_SIZE = CIRCLE_RADIUS / 2

        private enum class DotType {
            SMALL,
            MEDIUM,
            BIG,
        }
    }
}
