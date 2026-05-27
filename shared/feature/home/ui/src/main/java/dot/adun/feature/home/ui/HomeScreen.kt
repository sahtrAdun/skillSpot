package dot.adun.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.base.TopAppBar
import dot.adun.core.ui.components.vSpacer
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.home.ui.components.DummySearchField

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) = AppScreen(viewModel) { _, intents, actions ->
    AdunScaffold(
        screenActions = actions,
        appBar = {
            Column {
                TopAppBar(
                    bottomContent = {
                        DummySearchField { intents.navToSearch() }
                    }
                ) {
                    Text(
                        text = "Home Screen",
                        style = AppTheme.typography.subhead2,
                        color = AppTheme.colors.text.primary,
                    )
                }
            }
        }
    ) { offset ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            vSpacer(offset.y + 6.dp)
            items(20) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .heightIn(min = 30.dp)
                        .fillMaxWidth()
                        .surface(
                            color = AppTheme.colors.layer.surface,
                            shape = AppTheme.shapes.medium,
                            padding = PaddingValues(16.dp),
                            clickable = Clickable(
                                onClick = intents.navToDetails
                            )
                        )
                ) {
                    Text(
                        text = "number ${index + 1}",
                        color = AppTheme.colors.text.primary,
                        style = AppTheme.typography.body1
                    )
                }
            }
        }
    }
}
