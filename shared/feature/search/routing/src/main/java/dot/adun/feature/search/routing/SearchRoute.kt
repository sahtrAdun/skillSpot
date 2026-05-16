package dot.adun.feature.search.routing

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.Scene
import dot.adun.core.routing.Route
import dot.adun.core.routing.animations.transitionAnimationTween
import dot.adun.feature.search.ui.SearchScreen
import dot.adun.feature.search.ui.SearchViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class SearchRoute(
    override val id: String = "search_route"
) : Route<SearchViewModel> {
    @Composable
    override fun Screen(viewModel: SearchViewModel) {
        SearchScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): SearchViewModel = hiltViewModel<SearchViewModel>()

    override fun <T : NavKey> transitionSpec():
        (AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform) = {
            fadeIn(searchAnimationSpec()) togetherWith
                    fadeOut(searchAnimationSpec())
        }

    override fun <T : NavKey> popTransitionSpec():
        (AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform) = {
            fadeIn(searchAnimationSpec()) togetherWith
                    fadeOut(searchAnimationSpec())
        }

    override fun <T : NavKey> predictivePopTransitionSpec():
        AnimatedContentTransitionScope<Scene<T>>.(Int) -> ContentTransform = {
            ContentTransform(
                fadeIn(),
                fadeOut()
            )
        }

}

private fun <T> searchAnimationSpec() = transitionAnimationTween<T>(750)
