package dot.adun.feature.home.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.base.TopAppBar
import dot.adun.core.ui.components.buttons.IcButton
import dot.adun.core.ui.components.vSpacer
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.home.ui.components.DummySearchField

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) = AppScreen(viewModel) { _, intents ->
    AdunScaffold(
        appBar = {
            TopAppBar(
                leadingContent = {
                    IcButton(
                        vector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        onClick = {},
                        colors = AppTheme.presets.buttons.icon.regular,
                    )
                },
                trailingContent = {
                    IcButton(
                        vector = Icons.Default.History,
                        contentDescription = null,
                        onClick = {},
                        colors = AppTheme.presets.buttons.icon.regular,
                    )
                }
            ) {
                DummySearchField(intents.navToSearch)
            }
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.top),
        ) {

            vSpacer(padding.bottom)
        }
    }
}
