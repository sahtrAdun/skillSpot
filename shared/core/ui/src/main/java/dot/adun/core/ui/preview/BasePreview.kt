package dot.adun.core.ui.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun PreviewColumn(
    modifier: Modifier = Modifier,
    isDark: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    AppTheme(isDark) {
        Column(
            verticalArrangement = verticalArrangement,
            modifier = modifier
                .fillMaxSize()
                .surface(
                    color = AppTheme.colors.layer.background,
                    padding = AppTheme.paddings.inset.screen
                )
        ) {
            content()
        }
    }
}
