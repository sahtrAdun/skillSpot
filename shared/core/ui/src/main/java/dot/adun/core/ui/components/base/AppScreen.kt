package dot.adun.core.ui.components.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dot.adun.core.ui.core.BaseViewIntents
import dot.adun.core.ui.core.StateViewModel
import dot.adun.core.ui.core.event.snackbar.Snackbar
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.entity.rememberScreenActions
import dot.adun.core.ui.util.display
import dot.adun.core.ui.components.snackbar.CustomSnackbarHost as SnackbarHost
import dot.adun.core.ui.components.snackbar.CustomSnackbarHostState as SnackbarHostState
import dot.adun.core.ui.components.snackbar.Snackbar as SnackbarComponent

@Composable
fun <VS, VI: BaseViewIntents> AppScreen(
    viewModel: StateViewModel<VS, VI, *>,
    content: @Composable (state: VS, intents: VI, actions: ScreenActions) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val actions = rememberScreenActions()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is Snackbar -> snackbarHostState.showSnackbar(event)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content(
            state,
            viewModel.intents,
            actions
        )
    }

    SnackbarHost(
        hostState = snackbarHostState
    ) { snack ->
        SnackbarComponent(
            title = snack.visuals.title.display(),
            message = snack.visuals.message?.display(),
            isError = snack.visuals.isError,
            onDismiss = snack::dismiss
        )
    }
}
