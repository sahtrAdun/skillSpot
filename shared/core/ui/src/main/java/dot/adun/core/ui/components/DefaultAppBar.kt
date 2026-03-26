package dot.adun.core.ui.components

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.TopAppBar
import dot.adun.core.ui.components.buttons.BackIconButton
import dot.adun.core.ui.theme.AppTheme

@Composable
fun DefaultAppBar(
    label: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        backgroundColor = AppTheme.colors.layer.surface,
        leadingContent = {
            BackIconButton(onClick = onBackClick)
        }
    ) {
        Text(
            text = label,
            style = AppTheme.typography.subhead1,
            color = AppTheme.colors.text.primary
        )
    }
}
