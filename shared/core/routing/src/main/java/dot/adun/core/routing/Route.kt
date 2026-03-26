package dot.adun.core.routing

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.Scene
import dot.adun.core.ui.core.StateViewModel
import dot.adun.core.ui.core.event.ViewModelEvent
import kotlinx.coroutines.flow.collectLatest

interface Route<VM : StateViewModel<*, *, *>> : Navigation {
    @Composable
    fun Screen(viewModel: VM)

    @Composable
    fun viewModel(): VM

    @Composable
    fun Content(
        onScreenResult: (result: Any?) -> Unit,
        onNavigateBack: () -> Unit,
    ) {
        val viewModel = viewModel()

        LaunchedEffect(viewModel.result) {
            viewModel.result.collectLatest {
                onScreenResult(it)
            }
        }

        LaunchedEffect(viewModel.events) {
            viewModel.events.collect { event ->
                when (event) {
                    ViewModelEvent.NavigateBack -> onNavigateBack()
                }
            }
        }

        Screen(viewModel)
    }

    fun <T : NavKey> transitionSpec():
        (AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform)? = null

    fun <T : NavKey> popTransitionSpec():
        (AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform)? = null

    fun <T : NavKey> predictivePopTransitionSpec():
        (AnimatedContentTransitionScope<Scene<T>>.(Int) -> ContentTransform)? = null
}
