package dot.adun.feature.login.ui.screen

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.login.ui.component.LoginLayout

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) = AppScreen(viewModel) { state, intents ->
    LoginLayout(
        loadState = state.loadState,
        emailField = state.emailField,
        passwordField = state.passwordField,
        intents = intents
    )
}
