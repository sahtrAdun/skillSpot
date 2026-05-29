package dot.adun.core.ui.components.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun AdunScaffold(
    screenActions: ScreenActions,
    modifier: Modifier = Modifier,
    floatingContent: ScaffoldFloatingContent? = null,
    contentBackground: Color = AppTheme.colors.layer.background,
    appBar: (@Composable () -> Unit)? = null,
    bottomContent: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.(DpOffset) -> Unit
) {
    val density = LocalDensity.current
    var appBarHeightPx by remember { mutableIntStateOf(0) }
    var bottomContentHeightPx by remember { mutableIntStateOf(0) }

    Box {
        Box(
            modifier = modifier
                .fillMaxSize()
                .surface(
                    color = contentBackground,
                    clickable = Clickable(
                        onClick = screenActions::clearFocus,
                        indicationEnabled = false
                    )
                )
                .navigationBarsPadding()
        ) {
            with(density) {
                val offset = DpOffset(x = bottomContentHeightPx.toDp(), y = appBarHeightPx.toDp())
                content(offset)
            }
            if (bottomContent != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .onSizeChanged { bottomContentHeightPx = it.height }
                ) {
                    bottomContent()
                }
            }
            if (appBar != null) {
                Box(
                    modifier = Modifier
                        .onSizeChanged { appBarHeightPx = it.height }
                ) {
                    appBar()
                }
            }
        }

        if (floatingContent != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = floatingContent.alignment
            ) {
                floatingContent.content()
            }
        }
    }
}

@Stable
data class ScaffoldFloatingContent(
    val alignment: Alignment = Alignment.BottomEnd,
    val content: @Composable () -> Unit
)
