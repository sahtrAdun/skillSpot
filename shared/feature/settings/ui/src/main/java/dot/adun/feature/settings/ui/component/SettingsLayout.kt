package dot.adun.feature.settings.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.vSpacer
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.settings.domain.R
import dot.adun.feature.settings.ui.screen.SettingsViewIntents
import dot.adun.feature.settings.ui.screen.SettingsViewState

@Composable
fun SettingsLayout(
    state: SettingsViewState,
    intents: SettingsViewIntents
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.settings_screen_title),
                onBackClick = {},
                leadingContent = null
            )
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
            alignment = Alignment.TopEnd
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
            contentPadding = PaddingValues(
                horizontal = AppTheme.paddings.space.regular,
                vertical = 6.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.top)
        ) {
            itemsIndexed(state.settings) { _, setting ->
                SettingItem(setting = setting, intents = intents)
            }
            vSpacer(padding.bottom)
        }
    }
}
