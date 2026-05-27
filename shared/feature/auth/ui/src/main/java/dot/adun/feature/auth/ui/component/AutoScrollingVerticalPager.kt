package dot.adun.feature.auth.ui.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.time.Duration

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoScrollingVerticalPager(
    pageCount: Int,
    interval: Duration,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    content: @Composable (page: Int) -> Unit
) {
    if (pageCount <= 0) return

    val virtualPageCount = pageCount * 10000
    val initialVirtualPage = virtualPageCount / 2
    val startIndex = initialVirtualPage - (initialVirtualPage % pageCount)

    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { virtualPageCount }
    )

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isDragged, interval, pageCount) {
        if (!isDragged) {
            while (true) {
                delay(interval)
                if (!pagerState.isScrollInProgress) {
                    val nextPage = pagerState.currentPage + 1
                    try {
                        pagerState.animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(600)
                        )
                    } catch (_: Exception) {
                        // do nothing
                    }
                }
            }
        }
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = contentPadding,
        pageSize = pageSize,
    ) { page ->
        val index = page % pageCount

        Box(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                    alpha = lerp(
                        start = -0.75f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            content(index)
        }
    }
}
