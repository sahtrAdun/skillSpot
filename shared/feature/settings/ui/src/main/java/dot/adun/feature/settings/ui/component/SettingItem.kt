package dot.adun.feature.settings.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.settings.domain.entity.Setting
import dot.adun.feature.settings.ui.screen.SettingsViewIntents

@Composable
fun SettingItem(
    setting: Setting,
    intents: SettingsViewIntents
) {
    val onClick = when (setting) {
        is Setting.Toggle -> {
            { intents.toggleSetting(setting) }
        }
        is Setting.Selector -> {
            { intents.selectSetting(setting) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium,
            )
            .clickableEffect(Clickable.of(onClick))
            .padding(AppTheme.paddings.inset.content)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = setting.title.display(),
                    style = AppTheme.typography.subhead3,
                    color = AppTheme.colors.text.primary,
                )

                setting.description?.let { desc ->
                    Text(
                        text = desc.display(),
                        style = AppTheme.typography.caption2,
                        color = AppTheme.colors.text.tertiary,
                    )
                }
            }

            when (setting) {
                is Setting.Toggle -> {
                    Switch(
                        checked = setting.value,
                        onCheckedChange = { intents.toggleSetting(setting) },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = AppTheme.colors.layer.positive,
                            checkedThumbColor = AppTheme.colors.layer.background,
                            uncheckedTrackColor = AppTheme.colors.text.disabled,
                            uncheckedThumbColor = AppTheme.colors.layer.background,
                        )
                    )
                }

                is Setting.Selector -> {
                    Text(
                        text = setting.selectedOption?.label?.display() ?: "",
                        style = AppTheme.typography.body1,
                        color = AppTheme.colors.text.secondary,
                    )

                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        tint = AppTheme.colors.text.tertiary,
                    )
                }
            }
        }
    }
}
