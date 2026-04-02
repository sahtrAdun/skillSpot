package dot.adun.core.ui.preview.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.VSpacer
import dot.adun.core.ui.components.buttons.BackgroundButton
import dot.adun.core.ui.components.buttons.ButtonConfig
import dot.adun.core.ui.components.buttons.ButtonState
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.PrimaryTextButton
import dot.adun.core.ui.components.buttons.SecondaryButton
import dot.adun.core.ui.components.buttons.SurfaceButton
import dot.adun.core.ui.components.buttons.TertiaryButton
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.preview.PreviewColumn

@Preview
@Composable
private fun ButtonsPreview() {
    var state by remember { mutableStateOf<ButtonState>(ButtonState.Enabled) }

    PreviewColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryTextButton(
                text = if (state.enabled()) "Disable" else "Enable",
                clickable = Clickable(
                    onClick = { state = state.toggle() }
                ),
                config = ButtonConfig.primary(
                    size = ButtonConfig.Size.Small
                )
            )

            PrimaryTextButton(
                text = "Loader",
                clickable = Clickable(
                    onClick = {
                        state = state.load()
                    }
                ),
                config = ButtonConfig.primary(
                    size = ButtonConfig.Size.Small
                )
            )
        }

        VSpacer(24.dp)

        PrimaryButton(
            state = state,
            clickable = Clickable(onClick = {}),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Primary Button")
        }

        SecondaryButton(
            state = state,
            clickable = Clickable(onClick = {}),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Primary Button")
        }

        TertiaryButton(
            state = state,
            clickable = Clickable(onClick = {}),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Primary Button")
        }

        SurfaceButton(
            state = state,
            clickable = Clickable(onClick = {}),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Primary Button")
        }

        BackgroundButton(
            state = state,
            clickable = Clickable(onClick = {}),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Primary Button")
        }
    }
}
