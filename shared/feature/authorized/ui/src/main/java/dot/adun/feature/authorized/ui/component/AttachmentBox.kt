package dot.adun.feature.authorized.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun AttachmentBox(
    modifier: Modifier = Modifier,
    attachment: @Composable ColumnScope.() -> Unit,
    item: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.surface(
            color = AppTheme.colors.layer.background,
            shape = AppTheme.shapes.medium,
            border = Border(
                color = AppTheme.colors.border.primary,
                shape = AppTheme.shapes.medium,
            ),
        ),
    ) {
        item()
        attachment()
    }
}
