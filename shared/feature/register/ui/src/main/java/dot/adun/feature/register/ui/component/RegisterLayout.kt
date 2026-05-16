package dot.adun.feature.register.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.components.ContentLabel
import dot.adun.core.ui.components.WSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.textFields.PasswordTextField
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.register.ui.screen.Intents

@Composable
fun RegisterLayout(
    actions: ScreenActions,
    emailField: TextFieldData,
    passwordField: TextFieldData,
    intents: Intents,
    modifier: Modifier = Modifier
) {
    AdunScaffold(
        screenActions = actions,
        modifier = modifier
    ) { offset ->
        Column(
            modifier = Modifier
                .padding(top = offset)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .imePadding()
        ) {
            WSpacer()
            CenterContent(
                emailField = emailField,
                passwordField = passwordField,
                onEmailChange = intents.changeEmail,
                onPasswordChange = intents.changePassword
            )
            WSpacer()
            PrimaryButton(
                clickable = Clickable.of(intents.register),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.strings.button_register),
                )
            }
        }
    }
}

@Composable
private fun CenterContent(
    emailField: TextFieldData,
    passwordField: TextFieldData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium,
                padding = 16.dp
            )
    ) {
        ContentLabel(
            label = resRef(Res.strings.email)
        ) {
            SimpleTextField(
                data = emailField,
                onValueChange = onEmailChange,
                placeholder = "example@mail.com"
            )
        }

        ContentLabel(
            label = resRef(Res.strings.password)
        ) {
            PasswordTextField(
                data = passwordField,
                onValueChange = onPasswordChange,
            )
        }
    }
}
