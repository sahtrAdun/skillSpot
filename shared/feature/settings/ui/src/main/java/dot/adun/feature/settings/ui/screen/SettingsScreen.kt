package dot.adun.feature.settings.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) = AppScreen(viewModel) { _, _ ->
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Settings",
            style = AppTheme.typography.subhead2,
            color = AppTheme.colors.text.primary,
        )
    }
}
