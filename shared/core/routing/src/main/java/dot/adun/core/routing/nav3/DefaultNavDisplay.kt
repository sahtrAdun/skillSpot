package dot.adun.core.routing.nav3

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
import dot.adun.core.routing.Route
import dot.adun.core.routing.animations.transitionAnimationTween

@Composable
fun <T : NavKey> DefaultNavDisplay(
    stack: NavBackStack<T>,
    entryProvider: (key: T) -> NavEntry<T>,
    modifier: Modifier = Modifier
) {
    val lastKey = stack.lastOrNull()
    var previousKey by remember { mutableStateOf(lastKey) }

    val transitionSpec = remember(lastKey) {
        (lastKey as? Route<*>)
            ?.transitionSpec()
            ?: defaultTransition<T>()
    }

    val popTransitionSpec = remember(lastKey) {
        (previousKey as? Route<*>)
            ?.popTransitionSpec()
            ?: defaultPopTransition<T>()
    }

    val predictivePopTransitionSpec = remember(lastKey) {
        (lastKey as? Route<*>)
            ?.predictivePopTransitionSpec()
            ?: defaultPredictivePopTransition<T>()
    }

    LaunchedEffect(lastKey) {
        previousKey = lastKey
    }

    NavDisplay(
        backStack = stack,
        entryDecorators = rememberNavDecorators(),
        entryProvider = entryProvider,
        transitionSpec = transitionSpec,
        popTransitionSpec = popTransitionSpec,
        predictivePopTransitionSpec = predictivePopTransitionSpec,
        modifier = modifier
    )
}

private fun <T : NavKey> defaultTransition():
    AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
        slideInHorizontally(
            animationSpec = transitionAnimationTween()
        ) { it } togetherWith slideOutHorizontally(
            animationSpec = transitionAnimationTween()
        ) { -it }
    }

private fun <T : NavKey> defaultPopTransition():
    AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
        slideInHorizontally(
            animationSpec = transitionAnimationTween()
        ) { -it } togetherWith slideOutHorizontally(
            animationSpec = transitionAnimationTween()
        ) { it }
    }

private fun <T : NavKey> defaultPredictivePopTransition():
    AnimatedContentTransitionScope<Scene<T>>.(Int) -> ContentTransform =
    {
        ContentTransform(
            slideInHorizontally { -it },
            slideOutHorizontally { it }
        )
    }

const val TRANSITION_ANIMATION_DURATION = 500
