package dot.adun.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dot.adun.core.ui.components.loaders.Loader
import dot.adun.core.ui.components.loaders.LoaderAppearance
import dot.adun.core.ui.theme.AppTheme

@Composable
fun FullScreenLoader(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = { fadeIn(spec()) togetherWith fadeOut(spec()) },
        modifier = modifier.fillMaxSize()
    ) { loading ->
        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.15f))
            ) {
                Loader(
                    appearance = LoaderAppearance.Solid(
                        AppTheme.colors.layer.onSurface
                    )
                )
            }
        }
    }
}

@Composable
fun FullScreenLoader(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = { fadeIn(spec()) togetherWith fadeOut(spec()) },
        modifier = modifier.fillMaxSize()
    ) { loading ->
        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.15f))
            ) {
                content()
            }
        }
    }
}

private fun <T> spec() = tween<T>(500)
