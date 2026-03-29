package dot.adun.core.ui.components.progressBar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.VSpacer
import dot.adun.core.ui.preview.PreviewColumn
import dot.adun.core.ui.theme.AppTheme
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin


@Composable
fun WavyProgressBar(
    shape: Shape,
    modifier: Modifier = Modifier,
    progress: Float? = null,
    color: Color = AppTheme.colors.control.primary,
    strokeWidth: Dp = 2.dp,
    amplitude: Float = 6f,
    wavelength: Dp = 20.dp,
    waveSpeed: Dp = 10.dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "WaveTransition")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (1000 * (wavelength / waveSpeed)).toInt().coerceAtLeast(100),
                easing = LinearEasing
            )
        ),
        label = "Phase"
    )

    Canvas(modifier = modifier) {
        val outline = shape.createOutline(size, layoutDirection, this)
        val basePointPath = Path().apply {
            when (outline) {
                is Outline.Generic -> addPath(outline.path)
                is Outline.Rectangle -> addRect(outline.rect)
                is Outline.Rounded -> addRoundRect(outline.roundRect)
            }
        }

        val pathMeasure = PathMeasure()
        pathMeasure.setPath(basePointPath, false)
        val totalLength = pathMeasure.length
        val wavelengthPx = wavelength.toPx()

        val wavesCount = (totalLength / wavelengthPx).roundToInt().coerceAtLeast(1)
        val adjustedWavelengthPx = totalLength / wavesCount

        val step = 2f
        val wavyPath = Path()

        fun drawSegment(startDist: Float, endDist: Float) {
            var d = startDist
            var first = true
            while (d <= endDist) {
                val effectiveD = d % totalLength
                val pos = pathMeasure.getPosition(effectiveD)
                val tan = pathMeasure.getTangent(effectiveD)

                val angle = (d / adjustedWavelengthPx) * 2 * PI.toFloat() + phase
                val waveOffset = sin(angle) * amplitude

                val x = pos.x - tan.y * waveOffset
                val y = pos.y + tan.x * waveOffset

                if (first) wavyPath.moveTo(x, y) else wavyPath.lineTo(x, y)
                first = false
                d += step
            }
        }

        wavyPath.reset()

        if (progress != null) {
            drawSegment(0f, totalLength * progress.coerceIn(0f, 1f))
        } else {
            val numSegments = 2
            val segmentVisibleLength = totalLength / (numSegments * 2)
            val fullCycle = totalLength / numSegments

            val offset = (phase / (2 * PI.toFloat())) * fullCycle

            for (i in 0 until numSegments) {
                val start = i * fullCycle + offset
                drawSegment(start, start + segmentVisibleLength)
            }
        }

        drawPath(
            path = wavyPath,
            color = color,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewColumn {
        val infiniteTransition = rememberInfiniteTransition(label = "test")
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                    easing = LinearEasing
                )
            )
        )

        WavyProgressBar(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        VSpacer(24.dp)

        WavyProgressBar(
            progress = progress,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
    }
}
