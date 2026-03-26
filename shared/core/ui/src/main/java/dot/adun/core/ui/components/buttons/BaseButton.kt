package dot.adun.core.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.ClickableDefaults
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun BaseButton(
    clickable: Clickable,
    colors: ButtonColors,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shapes.medium,
    border: Border? = null,
    contentPaddings: PaddingValues = AppTheme.paddings.inset.content,
    borderVisibility: BorderVisibility = BorderVisibility.Newer,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val containerColor by animateColorAsState(if (enabled) colors.containerColor else colors.disabledContainerColor)
    val contentColor by animateColorAsState(if (enabled) colors.contentColor else colors.disabledContentColor)

    var buttonWidthPx by remember { mutableIntStateOf(0) }
    val radius = remember(buttonWidthPx) {
        with(density) {
            val width = buttonWidthPx.toDp()
            width * 0.9f
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onPlaced { buttonWidthPx = it.size.width }
            .clickableEffect(
                clickable = clickable.copy(
                    interactionSource = ClickableDefaults.interactionSource(),
                    indication = ClickableDefaults.defaultIndication(
                        radius = radius,
                        bounded = true
                    )
                ),
                scaleFactor = 0.97f
            )
            .heightIn(min = ButtonsDefault.height)
            .surface(
                color = containerColor,
                shape = shape,
                border = border ?: Border(
                    color = contentColor,
                    shape = shape
                )
                    .takeIf { borderVisibility.visible(false) }
            )
            .padding(contentPaddings)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}
