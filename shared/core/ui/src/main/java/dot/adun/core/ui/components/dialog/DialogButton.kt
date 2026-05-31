package dot.adun.core.ui.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.click
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display

@Composable
fun RowScope.DialogButton(
    text: TextRef,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .weight(1f)
            .clickableEffect(
                clickable = Clickable(
                    onClick = onClick,
                    enabled = enabled
                )
            )
            .padding(12.dp)
    ) {
        Text(
            text = text.display(),
            style = AppTheme.typography.body1,
            color = color,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}
