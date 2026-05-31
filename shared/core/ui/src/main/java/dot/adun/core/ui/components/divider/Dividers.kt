package dot.adun.core.ui.components.divider

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.theme.AppTheme

@Composable
fun ColumnScope.HDivider(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.layer.onSurface,
    thickness: Dp = 1.dp
) {
    HorizontalDivider(
        color = color,
        thickness = thickness,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun ColumnScope.HDivider(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.text.tertiary,
    thickness: Dp = 1.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            color = color,
            thickness = thickness,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = text,
            color = color,
            style = AppTheme.typography.body1
        )
        HorizontalDivider(
            color = color,
            thickness = thickness,
            modifier = Modifier.weight(1f)
        )
    }
}
