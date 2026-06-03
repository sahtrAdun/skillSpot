package dot.adun.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display

@Composable
fun ContentLabel(
    label: TextRef,
    modifier: Modifier = Modifier,
    labelModifier: Modifier = Modifier,
    color: Color = AppTheme.colors.text.primary,
    style: TextStyle = AppTheme.typography.body1,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {
        Text(
            text = label.display(),
            style = style,
            color = color,
            maxLines = maxLines,
            overflow = overflow,
            modifier = labelModifier
        )
        content()
    }
}
