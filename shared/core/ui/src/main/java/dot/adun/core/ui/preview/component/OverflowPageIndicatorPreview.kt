package dot.adun.core.ui.preview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.OverflowPageIndicator
import dot.adun.core.ui.components.base.VSpacer
import dot.adun.core.ui.preview.Preview

@Preview
@Composable
fun OverflowPageIndicatorPreview() {
    val actualSize = cards.size * 400
    val pagerState = rememberPagerState(
        pageCount = { actualSize },
        initialPage = actualSize / 2
    )

    Preview {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 16.dp
                ) { page ->
                    Card(cards[page % cards.size])
                }
                VSpacer(12.dp)
                OverflowPageIndicator(
                    pageCount = cards.size,
                    currentPage = pagerState.currentPage % cards.size
                )
            }
        }
    }
}

@Composable
private fun Card(data: CardData) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .background(Color.Blue.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "${data.name} -- ${data.number}",
            color = Color.White
        )
    }
}

private val cards = listOf(
    CardData("name1", 1),
    CardData("name2", 2),
    CardData("name3", 3),
    CardData("name4", 4),
    CardData("name5", 5),
    CardData("name6", 6),
    CardData("name7", 7),
    CardData("name8", 8),
    CardData("name9", 9),
    CardData("name10", 10),
    CardData("name11", 11),
    CardData("name12", 12),
    CardData("name13", 13),
    CardData("name14", 14),
    CardData("name15", 15),
)

@Immutable
private data class CardData(
    val name: String,
    val number: Int
)
