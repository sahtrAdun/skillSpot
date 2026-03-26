package dot.adun.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dot.adun.core.ui.components.base.vSpacer
import dot.adun.core.ui.theme.AppTheme

@Composable
fun VerticalList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    scrollEnabled: Boolean = true,
    reverse: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
        space = AppTheme.paddings.space.regular,
        alignment = if (!reverse) Alignment.Top else Alignment.Bottom
    ),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(AppTheme.paddings.inset.list),
        state = state,
        userScrollEnabled = scrollEnabled,
        reverseLayout = reverse,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        content()
        vSpacer()
    }
}

@Composable
fun VerticalList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    surfaceColor: Color = AppTheme.colors.layer.surface,
    scrollEnabled: Boolean = true,
    reverse: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
        space = AppTheme.paddings.space.regular,
        alignment = if (!reverse) Alignment.Top else Alignment.Bottom
    ),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    stickyContent: LazyItemScope.() -> Unit,
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .background(color = surfaceColor)
            .padding(AppTheme.paddings.inset.list),
        state = state,
        userScrollEnabled = scrollEnabled,
        reverseLayout = reverse,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        sticky(surfaceColor) { stickyContent() }
        content()
        vSpacer()
    }
}

private fun LazyListScope.sticky(
    color: Color,
    key: Any? = null,
    contentType: Any? = null,
    content: @Composable LazyItemScope.() -> Unit,
) {
    stickyHeader(
        key = key,
        contentType = contentType
    ) {
        Surface(color = color) {
            content()
        }
    }
}
