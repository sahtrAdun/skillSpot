package dot.adun.feature.login.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.resRef
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.ContentLabel
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.textFields.PasswordTextField
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.click
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.login.ui.screen.Intents

@Composable
fun LoginLayout(
    actions: ScreenActions,
    loadState: LoadState,
    emailField: TextFieldData,
    passwordField: TextFieldData,
    intents: Intents,
    modifier: Modifier = Modifier
) {
    AdunScaffold(
        screenActions = actions,
        modifier = modifier,
        appBar = {
            FloatingAppBar(
                label = stringResource(Res.strings.screen_login_title),
                onBackClick = intents.navigateBack
            )
        },
        bottomContent = {
            PrimaryButton(
                state = rememberButtonState(loadState.isLoading),
                clickable = Clickable.of(intents.login),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(Res.strings.button_login))
            }
        }
    ) { offset ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
            alignment = Alignment.TopEnd
        )
        MaterialShape(alignment = Alignment.BottomStart)

        Column(
            modifier = Modifier
                .padding(top = offset.y)
                .verticalScroll(rememberScrollState())
        ) {
            VSpacer(48.dp)
            Header()
            VSpacer(48.dp)
            CenterContent(
                enabled = !loadState.isLoading,
                emailField = emailField,
                passwordField = passwordField,
                onEmailChange = intents.changeEmail,
                onPasswordChange = intents.changePassword,
                onRegisterClick = intents.register,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = AppTheme.colors.layer.surface,
                        shape = AppTheme.shapes.large
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
            VSpacer(offset.x)
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(Res.strings.screen_login_header),
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.headline5,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(Res.strings.screen_login_desc),
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.subhead3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CenterContent(
    enabled: Boolean,
    emailField: TextFieldData,
    passwordField: TextFieldData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val passwordRequester = remember { FocusRequester() }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(vertical = 12.dp)
    ) {
        ContentLabel(
            label = resRef(Res.strings.email)
        ) {
            SimpleTextField(
                data = emailField,
                onValueChange = onEmailChange,
                placeholder = EMAIL_PLACEHOLDER,
                enabled = enabled,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                actions = KeyboardActions(
                    onNext = { passwordRequester.requestFocus() }
                )
            )
        }

        ContentLabel(
            label = resRef(Res.strings.password)
        ) {
            PasswordTextField(
                data = passwordField,
                onValueChange = onPasswordChange,
                enabled = enabled,
                actions = KeyboardActions(
                    onAny = { focusManager.clearFocus() }
                ),
                modifier = Modifier
                    .focusRequester(passwordRequester),
            )
        }

        FlowRow {
            Text(
                text = stringResource(Res.strings.screen_login_dont_have_account) + " ",
                color = AppTheme.colors.text.primary,
                style = AppTheme.typography.body2
            )
            Text(
                text = stringResource(Res.strings.button_register),
                color = AppTheme.colors.text.accent,
                style = AppTheme.typography.body2,
                modifier = Modifier.click(Clickable.of(onRegisterClick))
            )
        }
    }
}

private const val EMAIL_PLACEHOLDER = "example@mail.com"
