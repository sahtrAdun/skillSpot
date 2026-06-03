package dot.adun.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.ScaffoldPaddings

@Composable
fun <T> SimpleLayout(
    items: List<T>,
    padding: ScaffoldPaddings,
    modifier: Modifier = Modifier,
    item: @Composable (item: T, index: Int) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 6.dp),
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(items) { index, item -> item(item, index) }
        vSpacer(padding.bottom)
    }
}
