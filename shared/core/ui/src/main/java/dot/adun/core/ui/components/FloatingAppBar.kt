package dot.adun.core.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.TopAppBar
import dot.adun.core.ui.components.buttons.BackIconButton
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun FloatingAppBar(
    label: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        trailingContent = trailingContent,
        leadingContent = {
            BackIconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .surface(
                        color = AppTheme.colors.layer.surface,
                        shape = CircleShape,
                        padding = 4.dp
                    )
            )
        }
    ) {
        Text(
            text = label,
            style = AppTheme.typography.subhead2,
            color = AppTheme.colors.text.primary
        )
    }
}
