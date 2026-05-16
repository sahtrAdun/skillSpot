package dot.adun.core.ui.components.snackbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dot.adun.core.ui.core.event.snackbar.CustomSnackbarData

@Composable
fun CustomSnackbarHost(
    hostState: CustomSnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (CustomSnackbarData) -> Unit
) {
    val currentData = hostState.currentSnackbarData

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
    ) {
        AnimatedContent(
            targetState = currentData,
            contentAlignment = Alignment.TopCenter,
            transitionSpec = {
                fadeIn() + slideInVertically { -it } togetherWith
                        fadeOut() + slideOutVertically { -it }
            },
            modifier = Modifier.fillMaxWidth()
        ) {  data ->
            data?.let { snackbar(it) }
        }
    }
}
