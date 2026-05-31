package dot.adun.core.ui.components.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.unit.Dp
import dot.adun.core.ui.components.bottomBar.LocalBottomBarController
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.entity.rememberScreenActions
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun AdunScaffold(
    modifier: Modifier = Modifier,
    screenActions: ScreenActions = rememberScreenActions(),
    floatingContent: ScaffoldFloatingContent? = null,
    contentBackground: Color = AppTheme.colors.layer.background,
    appBar: (@Composable () -> Unit)? = null,
    bottomContent: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.(ScaffoldPaddings) -> Unit
) {
    val density = LocalDensity.current
    val bottomBarController = LocalBottomBarController.current
    var appBarHeightPx by remember { mutableIntStateOf(0) }
    var bottomContentHeightPx by remember { mutableIntStateOf(0) }
    val paddings = remember(
        appBarHeightPx,
        bottomContentHeightPx,
        bottomBarController.height
    ) {
        with(density) {
            ScaffoldPaddings(
                top = appBarHeightPx.toDp(),
                bottomContainer = bottomContentHeightPx.toDp(),
                bottomBar = bottomBarController.height
            )
        }
    }

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
            content(paddings)
            if (bottomContent != null) {
                Box(
                    modifier = Modifier
                        .padding(bottom = bottomBarController.height)
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

@Immutable
data class ScaffoldPaddings(
    val top: Dp,
    val bottomContainer: Dp,
    val bottomBar: Dp
) {
    val bottom: Dp = bottomContainer + bottomBar
}
