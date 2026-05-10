package dot.adun.core.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.textFields.core.TextFieldDefaults
import dot.adun.core.ui.theme.AppTheme

@Composable
fun TextFieldButton(
    @DrawableRes icon: Int,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null
) {
    IcButton(
        painter = painterResource(icon),
        colors = AppTheme.presets.buttons.icon.transparent.copy(
            contentColor = color
        ),
        onClick = onClick,
        contentDescription = description,
        contentPadding = PaddingValues(4.dp),
        modifier = modifier,
        iconModifier = Modifier.requiredSize(TextFieldDefaults.iconSize)
    )
}
