package dot.adun.core.ui.components.buttons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.theme.AppTheme

@Composable
fun PrimaryButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = AppTheme.presets.buttons.common.primary

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        content = content
    )
}

@Composable
fun PrimaryTextButton(
    label: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTheme.typography.body1
) {
    val colors = AppTheme.presets.buttons.common.primary

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}

@Composable
fun SecondaryButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = AppTheme.presets.buttons.common.secondary

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        content = content
    )
}

@Composable
fun SecondaryTextButton(
    label: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTheme.typography.body1
) {
    val colors = AppTheme.presets.buttons.common.secondary

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}

@Composable
fun TertiaryButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = AppTheme.presets.buttons.common.tertiary

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        content = content,
        borderVisibility = BorderVisibility.Always
    )
}

@Composable
fun TertiaryTextButton(
    label: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTheme.typography.body1
) {
    val colors = AppTheme.presets.buttons.common.tertiary

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        borderVisibility = BorderVisibility.Always
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}

@Composable
fun SurfaceButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = AppTheme.presets.buttons.common.onBackground

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        content = content,
        borderVisibility = BorderVisibility.Always,
        border = Border(
            color = colors.contentColor,
            width = 0.5.dp,
            shape = AppTheme.shapes.medium
        )
    )
}

@Composable
fun SurfaceTextButton(
    label: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTheme.typography.body1
) {
    val colors = AppTheme.presets.buttons.common.onBackground

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        borderVisibility = BorderVisibility.Always,
        border = Border(
            color = colors.contentColor,
            width = 0.5.dp,
            shape = AppTheme.shapes.medium
        )
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}

@Composable
fun BackgroundButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = AppTheme.presets.buttons.common.onSurface

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        content = content,
        borderVisibility = BorderVisibility.Always,
        border = Border(
            color = AppTheme.colors.layer.surface,
            width = 1.dp,
            shape = AppTheme.shapes.medium
        )
    )
}

@Composable
fun BackgroundTextButton(
    label: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTheme.typography.body1
) {
    val colors = AppTheme.presets.buttons.common.onSurface

    BaseButton(
        clickable = clickable,
        colors = colors,
        modifier = modifier,
        borderVisibility = BorderVisibility.Always,
        border = Border(
            color = AppTheme.colors.layer.surface,
            width = 1.dp,
            shape = AppTheme.shapes.medium
        )
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}
