package dot.adun.feature.settings.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.settings.domain.entity.Setting

@Composable
fun SettingSelectorDialogContent(
    options: List<Setting.Option>,
    selectedId: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedOptionId by remember { mutableIntStateOf(selectedId) }

    Column(modifier = modifier) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableEffect(
                        Clickable.of {
                            selectedOptionId = option.id
                            onSelect(option.id)
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                RadioButton(
                    selected = selectedOptionId == option.id,
                    onClick = {
                        selectedOptionId = option.id
                        onSelect(option.id)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppTheme.colors.control.primary,
                        unselectedColor = AppTheme.colors.text.disabled,
                    )
                )

                Text(
                    text = option.label.display(),
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text.primary,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}
