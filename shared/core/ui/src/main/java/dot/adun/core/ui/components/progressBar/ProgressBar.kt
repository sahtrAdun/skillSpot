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

@Composable
fun ProgressBar(
    shape: Shape,
    modifier: Modifier = Modifier,
    progress: Float? = null,
    color: Color = AppTheme.colors.control.primary,
    strokeWidth: Dp = 2.dp,
    speed: Int = 3000,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ProgressTransition")
    val animationOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = speed, easing = LinearEasing)
        ),
        label = "Offset"
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

        val drawPath = Path()

        if (progress != null) {
            pathMeasure.getSegment(
                startDistance = 0f,
                stopDistance = totalLength * progress.coerceIn(0f, 1f),
                destination = drawPath
            )
        } else {
            val numSegments = 2
            val segmentLength = totalLength / 4f
            val sectionLength = totalLength / numSegments

            for (i in 0 until numSegments) {
                val start = (i * sectionLength + animationOffset * totalLength) % totalLength
                val end = start + segmentLength

                if (end > totalLength) {
                    pathMeasure.getSegment(start, totalLength, drawPath, true)
                    pathMeasure.getSegment(0f, end % totalLength, drawPath, true)
                } else {
                    pathMeasure.getSegment(start, end, drawPath, true)
                }
            }
        }

        drawPath(
            path = drawPath,
            color = color,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
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

        ProgressBar(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        VSpacer(24.dp)

        ProgressBar(
            progress = progress,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
    }
}
