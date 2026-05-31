package dot.adun.feature.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun DummySearchField(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = CircleShape,
                padding = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
            )
            .clickableEffect(Clickable.of(onClick))
    ) {
        Text(
            text = "Search",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text.primary
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = AppTheme.colors.icon.secondary,
            modifier = Modifier.requiredSize(18.dp)
        )
    }
}
