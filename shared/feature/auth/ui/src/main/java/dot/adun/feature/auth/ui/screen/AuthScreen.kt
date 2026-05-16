package dot.adun.feature.auth.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.modifiers.click.Clickable

@Composable
fun AuthScreen(
    viewModel: AuthViewModel
) = AppScreen(viewModel) { _, intents, _ ->
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        PrimaryButton(
            clickable = Clickable.of(intents.register)
        ) {
            Text("To registration")
        }
    }
}
