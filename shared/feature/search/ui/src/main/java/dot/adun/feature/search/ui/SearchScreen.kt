package dot.adun.feature.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.ui.components.DefaultAppBar
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.buttons.BackgroundTextButton
import dot.adun.core.ui.components.buttons.PrimaryTextButton
import dot.adun.core.ui.components.buttons.SecondaryTextButton
import dot.adun.core.ui.components.buttons.SurfaceTextButton
import dot.adun.core.ui.components.buttons.TertiaryTextButton
import dot.adun.core.ui.components.textFields.PasswordTextField
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.EffectType
import dot.adun.core.ui.modifiers.effect
import dot.adun.core.ui.theme.AppTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) = AppScreen(viewModel) { state, intents, actions ->
    AdunScaffold(
        screenActions = actions,
        appBar = {
            DefaultAppBar(
                label = "Test search",
                onBackClick = intents.navigateBack
            )
        }
    ) { offset ->
        Column(
            modifier = Modifier
                .padding(AppTheme.paddings.inset.screen)
                .padding(top = offset)
        ) {
            PasswordTextField(
                data = state.textField,
                onValueChange = intents.updateText,
                modifier = Modifier
                    .effect(
                        effect = EffectType.Shake,
                        trigger = { state.textField.errorFocusRequired }
                    )
            )
            VSpacer(52.dp)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                PrimaryTextButton(
                    text = "Validate",
                    clickable = Clickable(onClick = intents.validateText),
                    modifier = Modifier.fillMaxWidth()
                )

                SecondaryTextButton(
                    text = "Validate",
                    clickable = Clickable(onClick = intents.validateText),
                    modifier = Modifier.fillMaxWidth()
                )

                TertiaryTextButton(
                    text = "Validate",
                    clickable = Clickable(onClick = intents.validateText),
                    modifier = Modifier.fillMaxWidth()
                )

                SurfaceTextButton(
                    text = "Validate",
                    clickable = Clickable(onClick = intents.validateText),
                    modifier = Modifier.fillMaxWidth()
                )

                BackgroundTextButton(
                    text = "Validate",
                    clickable = Clickable(onClick = intents.validateText),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}