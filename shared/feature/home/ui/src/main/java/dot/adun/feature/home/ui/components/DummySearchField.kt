package dot.adun.feature.home.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun DummySearchField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .padding(AppTheme.paddings.inset.content)
            .heightIn(min = 52.dp)
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.background,
                shape = AppTheme.shapes.medium,
                padding = 12.dp,
                clickable = Clickable(
                    indicationEnabled = false,
                    onClick = onClick
                )
            )
    ) {
        Text(
            text = "Search",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text.primary
        )
    }
}
