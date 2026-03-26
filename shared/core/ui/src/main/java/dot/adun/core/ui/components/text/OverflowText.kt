package dot.adun.core.ui.components.text

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.theme.AppTheme

@Composable
fun OverflowText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.text.primary,
    style: TextStyle = AppTheme.typography.caption1,
    maxLines: Int = 1,
) {
    var isOverflowed by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Text(
            text = text,
            maxLines = maxLines,
            color = color,
            overflow = TextOverflow.Ellipsis,
            style = style.copy(
                textDecoration = if (isOverflowed) TextDecoration.Underline else null
            ),
            onTextLayout = { layoutResult ->
                isOverflowed = layoutResult.hasVisualOverflow
            },
            modifier = Modifier.clickable(enabled = isOverflowed) {
                isMenuExpanded = true
            }
        )

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            shape = AppTheme.shapes.medium,
            containerColor = AppTheme.colors.layer.surface,
            shadowElevation = 8.dp,
            border = BorderStroke(
                width = 0.5.dp,
                color = AppTheme.colors.border.hint
            ),
            modifier = Modifier
                .widthIn(max = 350.dp)
        ) {
            Text(
                text = text,
                style = style,
                color = AppTheme.colors.text.primary,
                modifier = Modifier
                    .padding(AppTheme.paddings.inset.content)
            )
        }
    }
}
