package dot.adun.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun OverflowPageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    visibleCount: Int = 9,
    dotSize: Dp = 8.dp,
    dotSpacing: Dp = 10.dp,
    inactiveColor: Color = AppTheme.colors.control.tertiary,
    activeColor: Color = AppTheme.colors.control.primary,
) {
    require(visibleCount >= MINIMUM_VISIBLE_COUNT) {
        "OverflowPageIndicator: minimum visible dot's count must be at least $MINIMUM_VISIBLE_COUNT"
    }
    if (pageCount <= 0) return

    val page = currentPage.coerceIn(0, pageCount - 1)
    val overflowNeeded = pageCount > visibleCount

    val windowStart = remember(page, pageCount, visibleCount, overflowNeeded) {
        if (!overflowNeeded) {
            0
        } else {
            val margin = (visibleCount - 1) / 2
            (page - margin).coerceIn(0, pageCount - visibleCount)
        }
    }

    val leftOverflow = windowStart > 0
    val rightOverflow = windowStart + visibleCount < pageCount
    val animatedOffset by animateFloatAsState(
        targetValue = windowStart.toFloat(),
        animationSpec = tween(ANIMATION_DURATION),
        label = "OverflowPageIndicatorOffset",
    )

    val viewportSlots = remember(visibleCount, pageCount) { minOf(visibleCount, pageCount) }
    Layout(
        modifier = modifier.clipToBounds(),
        content = {
            for (index in 0 until pageCount) {
                PageDot(
                    selected = index == page,
                    scale = dotScaleFor(
                        position = index - windowStart,
                        visibleCount = visibleCount,
                        leftOverflow = leftOverflow,
                        rightOverflow = rightOverflow,
                        overflowNeeded = overflowNeeded,
                    ),
                    size = dotSize,
                    color = inactiveColor,
                    selectedColor = activeColor,
                )
            }
        },
    ) { measurables, _ ->
        val slotPx = (dotSize + dotSpacing).roundToPx()
        val widthPx = slotPx * viewportSlots
        val heightPx = (dotSize * SELECTED_DOT_SCALE).roundToPx()
        val offsetPx = (animatedOffset * slotPx).roundToInt()

        val placeable = measurables.map { it.measure(Constraints()) }

        layout(widthPx, heightPx) {
            placeable.forEachIndexed { index, placeable ->
                val slotStart = index * slotPx - offsetPx
                val x = slotStart + (slotPx - placeable.width) / 2
                val y = (heightPx - placeable.height) / 2
                placeable.placeRelative(x, y)
            }
        }
    }
}

/**
 * Scale of a dot given its [position] within the visible window
 * (`index - windowStart`). Dots outside the window — or sitting on an overflow
 * edge — are shrunk; the dot one step inward from an overflow edge is rendered
 * at a medium size to hint that more pages exist.
 */
private fun dotScaleFor(
    position: Int,
    visibleCount: Int,
    leftOverflow: Boolean,
    rightOverflow: Boolean,
    overflowNeeded: Boolean,
): Float {
    if (!overflowNeeded) return 1f
    return when {
        position < 0 || position > visibleCount - 1 -> SMALL_DOT_SCALE
        leftOverflow && position == 0 -> SMALL_DOT_SCALE
        leftOverflow && position == 1 -> MEDIUM_DOT_SCALE
        rightOverflow && position == visibleCount - 1 -> SMALL_DOT_SCALE
        rightOverflow && position == visibleCount - 2 -> MEDIUM_DOT_SCALE
        else -> 1f
    }
}

@Composable
private fun PageDot(
    selected: Boolean,
    scale: Float,
    color: Color,
    selectedColor: Color,
    size: Dp = 8.dp,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected) selectedColor else color,
        animationSpec = tween(ANIMATION_DURATION),
        label = "PageDotColor",
    )
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(ANIMATION_DURATION),
        label = "PageDotScale",
    )

    Box(
        modifier = Modifier
            .scale(animatedScale)
            .size(size)
            .background(animatedColor, CircleShape)
    )
}

private const val SMALL_DOT_SCALE = 0.5f
private const val MEDIUM_DOT_SCALE = 0.8f
private const val SELECTED_DOT_SCALE = 1.1f
private const val ANIMATION_DURATION = 500
private const val MINIMUM_VISIBLE_COUNT = 1
