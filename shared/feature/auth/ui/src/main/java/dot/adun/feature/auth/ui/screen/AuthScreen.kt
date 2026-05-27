package dot.adun.feature.auth.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.loaders.Loader
import dot.adun.core.ui.components.loaders.LoaderAppearance
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.auth.domain.entity.AuthStatus
import dot.adun.feature.auth.ui.component.AuthLayout

@Composable
fun AuthScreen(
    viewModel: AuthViewModel
) = AppScreen(viewModel) { state, intents, actions ->
    AnimatedContent(
        targetState = state.authStatus,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { status ->
        if (status is AuthStatus.Loading) {
            LoadState()
        } else {
            AuthLayout(
                actions = actions,
                intents = intents
            )
        }
    }
}

@Composable
private fun LoadState(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.layer.background)
    ) {
        Loader(
            appearance = LoaderAppearance.Solid(AppTheme.colors.layer.onSurface),
            size = 64.dp
        )
        Text(
            text = stringResource(Res.strings.await),
            color = AppTheme.colors.text.tertiary,
            style = AppTheme.typography.body1
        )
    }
}
