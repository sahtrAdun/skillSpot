package dot.adun.feature.auth.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.components.dialog.DialogButton
import dot.adun.core.ui.components.divider.HDivider
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.auth.ui.R
import dot.adun.core.domain.entity.UserRole

@Composable
fun ChooseRoleDialogContent(
    onConfirm: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRole by remember { mutableStateOf(UserRole.availableValues().first()) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.dialog_desc),
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.body1,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        )

        UserRole.availableValues().forEach { role ->
            RoleRow(
                selectedRole = selectedRole,
                role = role,
                onClick = { selectedRole = role }
            )
        }

        HDivider()
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DialogButton(
                text = resRef(Res.strings.button_confirm),
                color = AppTheme.colors.text.accent,
                onClick = { onConfirm(selectedRole) }
            )
        }
    }
}

@Composable
private fun RoleRow(
    selectedRole: UserRole,
    role: UserRole,
    onClick: () -> Unit
) {
    val selected = selectedRole == role

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickableEffect(
                clickable = Clickable(
                    onClick = onClick,
                    enabled = !selected
                )
            )
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonColors(
                selectedColor = AppTheme.colors.text.accent,
                unselectedColor = AppTheme.colors.text.primary,
                disabledSelectedColor = AppTheme.colors.text.disabled,
                disabledUnselectedColor = AppTheme.colors.text.disabled
            )
        )
        Text(
            text = role.name,
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.body1
        )
    }
}
