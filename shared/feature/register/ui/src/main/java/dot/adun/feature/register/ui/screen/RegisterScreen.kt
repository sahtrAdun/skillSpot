package dot.adun.feature.register.ui.screen

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.register.ui.component.RegisterLayout

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel
) = AppScreen(viewModel) { state, intents, actions ->
    RegisterLayout(
        actions = actions,
        emailField = state.emailField,
        passwordField = state.passwordField,
        intents = intents
    )
}
