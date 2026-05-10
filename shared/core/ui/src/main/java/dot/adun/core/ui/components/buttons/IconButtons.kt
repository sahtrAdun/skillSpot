package dot.adun.core.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.ClickableDefaults
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import kotlin.math.sqrt

@Composable
internal fun MyIconButton(
    colors: IconButtonColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = AppTheme.shapes.medium,
    contentPadding: PaddingValues = ButtonsDefault.Icons.contentPadding,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val containerColor by animateColorAsState(if (enabled) colors.containerColor else colors.disabledContainerColor)
    val contentColor by animateColorAsState(if (enabled) colors.contentColor else colors.disabledContentColor)

    var contentSize by remember { mutableStateOf<IntSize?>(null) }
    val actualSizeDp = remember(contentSize) {
        with(density) {
            contentSize?.let { (w, h) ->
                val width = w.toDp().value
                val height = h.toDp().value
                sqrt(width * height).dp
            }
                ?: 0.dp
        }
    }

    Box(
        modifier = modifier
            .onPlaced { contentSize = it.size }
            .clickableEffect(
                clickable = Clickable(
                    onClick = onClick,
                    enabled = enabled,
                    interactionSource = ClickableDefaults.interactionSource(),
                    indication = ripple(
                        bounded = false,
                        radius = actualSizeDp
                    )
                )
            )
            .surface(
                color = containerColor,
                shape = shape,
                padding = contentPadding
            )
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

@Composable
fun IcButton(
    painter: Painter,
    colors: IconButtonColors,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    shape: RoundedCornerShape = AppTheme.shapes.medium,
    contentPadding: PaddingValues = ButtonsDefault.Icons.contentPadding,
    enabled: Boolean = true,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    MyIconButton(
        modifier = modifier,
        colors = colors,
        shape = shape,
        enabled = enabled,
        onClick = onClick,
        contentPadding = contentPadding
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = iconModifier
        )
    }
}

@Composable
fun BackIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IcButton(
        modifier = modifier,
        painter = painterResource(Res.drawable.ic_back_24),
        colors = AppTheme.presets.buttons.icon.transparent,
        onClick = onClick,
        iconModifier = Modifier.requiredSize(16.dp)
    )
}
