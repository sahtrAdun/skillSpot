package dot.adun.core.ui.components.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dot.adun.core.ui.core.BaseViewIntents
import dot.adun.core.ui.core.StateViewModel
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.entity.rememberScreenActions

@Composable
fun <VS, VI: BaseViewIntents> AppScreen(
    viewModel: StateViewModel<VS, VI, *>,
    content: @Composable (state: VS, intents: VI, actions: ScreenActions) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val actions = rememberScreenActions()

    Box(modifier = Modifier.fillMaxSize()) {
        content(
            state,
            viewModel.intents,
            actions
        )
    }
}
