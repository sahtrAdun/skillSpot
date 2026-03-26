package dot.adun.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.theme.AppTheme

@Composable
fun OverflowPageIndicator(
    pageCount: Int,
    currentPage: Int,
    visibleCount: Int = 9,
    dotSize: Dp = 8.dp,
    dotSpacing: Dp = 10.dp,
    inactiveColor: Color = AppTheme.colors.control.tertiary,
    activeColor: Color = AppTheme.colors.control.primary
) {
    require(visibleCount >= MINIMUM_VISIBLE_COUNT) {
        error("OverflowPageIndicator: minimum visible dot's count must be at least 5")
    }
    if (pageCount <= 0) return

    val state = rememberLazyListState()
    val width = dotSize * visibleCount + dotSpacing * visibleCount - dotSpacing / 4
    val overflowNeeded = visibleCount < pageCount
    val sideCount = 2
    val step = 1

    var prevPage by remember { mutableIntStateOf(currentPage) }
    val lastPageIndex = remember(pageCount) { pageCount - 1 }
    val firstVisibleIndex = remember(state.firstVisibleItemIndex) { state.firstVisibleItemIndex }
    val lastVisibleIndex = remember(firstVisibleIndex) { firstVisibleIndex + visibleCount - 1 }
    val leftDepth = remember(firstVisibleIndex) {
        when {
            firstVisibleIndex == 1 -> Depth.One
            firstVisibleIndex > 1 -> Depth.Unbound
            else -> Depth.Zero
        }
    }
    val rightDepth = remember(lastVisibleIndex) {
        when {
            lastVisibleIndex == lastPageIndex - 1 -> Depth.One
            lastVisibleIndex < lastPageIndex - 1 -> Depth.Unbound
            else -> Depth.Zero
        }
    }

    LaunchedEffect(Unit, pageCount) {
        if (currentPage !in firstVisibleIndex..lastVisibleIndex) {
            val center = currentPage - ((visibleCount - 1) / 2)
            state.animateScrollToItem(center)
        }
    }

    LaunchedEffect(currentPage) {
        if (!overflowNeeded) {
            return@LaunchedEffect
        }

        if (currentPage !in firstVisibleIndex..lastVisibleIndex) {
            state.animateScrollToItem(currentPage)
            return@LaunchedEffect
        }

        val offset = if (visibleCount == MINIMUM_VISIBLE_COUNT) step else sideCount

        when {
            currentPage > prevPage && currentPage >= lastVisibleIndex - offset &&
                    rightDepth != Depth.Zero -> {
                if (currentPage + sideCount >= lastPageIndex) {
                    state.animateScrollToItem(lastVisibleIndex)
                } else {
                    state.animateScrollToItem(firstVisibleIndex + step)
                }
            }

            currentPage < prevPage && currentPage <= firstVisibleIndex + offset &&
                    leftDepth != Depth.Zero -> {
                if (currentPage - sideCount <= 0) {
                    state.animateScrollToItem(0)
                } else {
                    state.animateScrollToItem(firstVisibleIndex - step)
                }
            }
        }

        prevPage = currentPage
    }

    LazyRow(
        state = state,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(width)
            .height(dotSize * SELECTED_DOT_SCALE)
    ) {
        items(pageCount) { index ->
            val inLeftSide = index in firstVisibleIndex..firstVisibleIndex + step
            val inRightSide = index in lastVisibleIndex - step..lastVisibleIndex

            val dotScale = if (!overflowNeeded) 1f else when {
                index in firstVisibleIndex + sideCount..lastVisibleIndex - sideCount -> 1f
                inLeftSide -> when {
                    leftDepth != Depth.Zero && index == firstVisibleIndex + step -> MEDIUM_DOT_SCALE
                    leftDepth == Depth.Zero -> 1f
                    else -> SMALL_DOT_SCALE
                }
                inRightSide -> when {
                    rightDepth != Depth.Zero && index == lastVisibleIndex - step -> MEDIUM_DOT_SCALE
                    rightDepth == Depth.Zero -> 1f
                    else -> SMALL_DOT_SCALE
                }
                else -> SMALL_DOT_SCALE
            }

            PageDot(
                selected = currentPage == index,
                scale = dotScale,
                size = dotSize,
                spacing = dotSpacing,
                color = inactiveColor,
                selectedColor = activeColor
            )
        }
    }
}

@Composable
private fun PageDot(
    selected: Boolean,
    scale: Float,
    color: Color,
    selectedColor: Color,
    size: Dp = 8.dp,
    spacing: Dp = 6.dp,
) {
    val color by animateColorAsState(
        targetValue = if (selected) selectedColor else color,
        animationSpec = tween(ANIMATION_DURATION)
    )
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(ANIMATION_DURATION)
    )

    Box(
        modifier = Modifier
            .padding(horizontal = spacing / 2)
            .scale(animatedScale)
            .size(size)
            .background(color, CircleShape)
    )
}

private enum class Depth {
    Zero, One, Unbound
}

private const val SMALL_DOT_SCALE = 0.5f
private const val MEDIUM_DOT_SCALE = 0.8f
private const val SELECTED_DOT_SCALE = 1.1f
private const val ANIMATION_DURATION = 500
private const val MINIMUM_VISIBLE_COUNT = 5
