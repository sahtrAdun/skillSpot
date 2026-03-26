package dot.adun.core.ui.components.base

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun AdunScaffold(
    screenActions: ScreenActions,
    modifier: Modifier = Modifier,
    appBar: (@Composable () -> Unit)? = null,
    floatingContent: ScaffoldFloatingContent? = null,
    contentBackground: Color = AppTheme.colors.layer.background,
    content: @Composable (yOffset: Dp) -> Unit
) {
    val density = LocalDensity.current
    var appBarHeightPx by remember { mutableIntStateOf(0) }

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
            with(density) { content(appBarHeightPx.toDp()) }
            if (appBar != null) {
                Box(
                    modifier = Modifier
                        .onPlaced { appBarHeightPx = it.size.height }
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
