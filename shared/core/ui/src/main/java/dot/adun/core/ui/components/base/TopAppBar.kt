package dot.adun.core.ui.components.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    backgroundColor: Color = Color.Transparent,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    bottomContent: (@Composable () -> Unit)? = null,
    centralContent: @Composable () -> Unit
) {
    val density = LocalDensity.current
    var leadingWidthPx by remember { mutableStateOf<Int?>(null) }
    var trailingWidthPx by remember { mutableStateOf<Int?>(null) }

    Box {
        Box(
            modifier= Modifier
                .matchParentSize()
                .background(color = backgroundColor)
        )

        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .displayCutoutPadding()
                    .padding(padding)
                    .padding(AppTheme.paddings.inset.content),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.semiRegular),
                verticalAlignment = Alignment.CenterVertically
            ) {
                density.SideContent(
                    placeholderWidthPx = trailingWidthPx,
                    onWidthChanges = { leadingWidthPx = it },
                    content = leadingContent
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    centralContent()
                }
                density.SideContent(
                    placeholderWidthPx = leadingWidthPx,
                    onWidthChanges = { trailingWidthPx = it },
                    content = trailingContent
                )
            }

            bottomContent?.invoke()
        }
    }
}

@Composable
private fun Density.SideContent(
    placeholderWidthPx: Int?,
    onWidthChanges: (Int) -> Unit,
    content: (@Composable () -> Unit)?
) {
    if (content != null) {
        Box(
            modifier = Modifier
                .onPlaced { onWidthChanges(it.size.width) }
        ) {
            content()
        }
    } else if (placeholderWidthPx != null) {
        HSpacer(placeholderWidthPx.toDp())
    }
}
