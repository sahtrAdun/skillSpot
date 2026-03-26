package dot.adun.core.ui.modifiers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Modifier.shimmer(
    cornerRadius: Dp = 0.dp
): Modifier {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(DURATION.toLong())
        visible = false
    }

    return this.shimmer(
        cornerRadius = cornerRadius,
        visible = visible
    )
}

@Composable
fun Modifier.shimmer(
    visible: Boolean,
    cornerRadius: Dp = 0.dp,
): Modifier {
    val shimmerColors = listOf(
        Color.DarkGray.copy(alpha = alpha(0.1f, visible)),
        Color.Gray.copy(alpha = alpha(0.5f, visible)),
        Color.Gray.copy(alpha = alpha(0.1f, visible))
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -400f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = DURATION,
                easing = FastOutSlowInEasing
            )
        ),
        label = "Translate"
    )

    return this
        .drawBehind {
            val brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnim, 0f),
                end = Offset(translateAnim + size.width / 1.5f, size.height)
            )
            val cornerPx = cornerRadius.toPx()
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerPx, cornerPx),
                size = size
            )
        }
}

@Composable
private fun alpha(
    targetAlpha: Float,
    visible: Boolean
): Float {
    return animateFloatAsState(
        targetValue = if (visible) targetAlpha else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "ShimmerAlpha"
    )
        .value
}

private const val DURATION = 1600
