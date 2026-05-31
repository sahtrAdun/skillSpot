package dot.adun.routing.nav3

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.DefaultNavDisplay
import dot.adun.core.ui.components.bottomBar.BottomBar
import dot.adun.core.ui.components.bottomBar.BottomBarController
import dot.adun.core.ui.components.bottomBar.BottomBarTab
import dot.adun.core.ui.components.bottomBar.LocalBottomBarController
import dot.adun.feature.auth.routing.AuthFlow
import dot.adun.feature.auth.routing.AuthFlowResult
import dot.adun.feature.auth.routing.AuthFlowState
import dot.adun.feature.auth.routing.authFlow
import dot.adun.feature.authorized.routing.routes.ActiveRoute
import dot.adun.feature.home.routing.HomeFlow
import dot.adun.feature.home.routing.HomeFlowResult
import dot.adun.feature.home.routing.homeFlow
import dot.adun.feature.home.routing.routes.HomeRoute
import dot.adun.feature.settings.routing.SettingsRoute
import dot.adun.routing.nav3.mappers.toRoute

@Composable
fun AppNavigation(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stack = rememberNavBackStack(initialFlow.startDestination)
    val rootFlow = remember(stack.firstOrNull()) {
        (stack.firstOrNull() as? Flow) ?: initialFlow
    }
    val flowState = remember { mutableMapOf(rootFlow to rootFlow.state) }
    val flowQueue = remember { mutableSetOf<Flow>() }
    val bottomBarController = remember { BottomBarController() }

    LaunchedEffect(stack.lastOrNull()) {
        val route = stack.lastOrNull()
        when (route) {
            is HomeRoute -> bottomBarController.selectTab(BottomBarTab.Home)
            is ActiveRoute -> bottomBarController.selectTab(BottomBarTab.Active)
            is SettingsRoute -> bottomBarController.selectTab(BottomBarTab.Settings)
        }
        bottomBarController.isVisible = bottomBarVisible(route)
    }

    CompositionLocalProvider(
        LocalBottomBarController provides bottomBarController
    ) {
        Box(modifier = modifier) {
            DefaultNavDisplay(
                stack = stack,
                modifier = Modifier.fillMaxSize(),
                entryProvider = appEntryProvider(
                    backStack = stack,
                    flow = rootFlow,
                    flowState = flowState,
                    flowQueue = flowQueue
                ) {
                    authFlow { result ->
                        when (result) {
                            AuthFlowResult.Finish -> onFinish()
                            AuthFlowResult.AuthorizationSuccess -> {
                                replaceAll(HomeFlow)
                            }
                        }
                    }

                    homeFlow { result ->
                        when (result) {
                            HomeFlowResult.Finish -> onFinish()
                            HomeFlowResult.Logout -> {
                                val state = AuthFlowState(AuthFlowState.Routes.Login)
                                replaceAll(AuthFlow(state))
                            }
                        }
                    }
                }
            )

            AppBottomBar(bottomBarController, stack)
        }
    }
}

@Composable
private fun BoxScope.AppBottomBar(
    bottomBarController: BottomBarController,
    stack: NavBackStack<NavKey>
) {
    val density = LocalDensity.current
    val navigationInsets = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    AnimatedContent(
        targetState = bottomBarController.isVisible,
        transitionSpec = {
            slideInVertically(bottomBarTransitionSpec()) { it } togetherWith
                    slideOutVertically(bottomBarTransitionSpec()) { it }
        },
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) { visible ->
        if (visible) {
            BottomBar(
                selectedTab = bottomBarController.selectedTab,
                onTabSelected = { tab -> stack.distinctTab(tab.toRoute()) },
                modifier = Modifier
                    .onSizeChanged {
                        with(density) {
                            val height = (it.height.toDp() - navigationInsets)
                                .coerceAtLeast(navigationInsets)

                            bottomBarController.setHeight(height)
                        }
                    }
            )
        } else {
            Box(modifier = Modifier.fillMaxWidth())
        }
    }
}

private fun appEntryProvider(
    backStack: NavBackStack<NavKey>,
    flow: Flow,
    flowState: MutableMap<Flow, Any?>,
    flowQueue: MutableSet<Flow>,
    content: NavFlowScope.() -> Unit
): (NavKey) -> NavEntry<NavKey> = entryProvider {
    val scope = NavFlowScope(
        stack = backStack,
        flowParent = flow.startDestination,
        flowState = flowState,
        flowQueue = flowQueue,
        entryProviderScope = this
    )
    scope.content()
}

private fun NavBackStack<NavKey>.distinctTab(route: NavKey) {
    if (lastOrNull() == route) return

    if (contains(route)) {
        remove(route)
    }

    if (route is Flow) {
        add(route.startDestination)
    } else {
        add(route)
    }

    if (route is HomeRoute) {
        while (size > 1) removeAt(0)
    }
}

private fun bottomBarVisible(route: NavKey?): Boolean {
    return route?.let { it::class in bottomBarRoutes } ?: false
}

fun <T> bottomBarTransitionSpec(): TweenSpec<T> = tween(400)
private val bottomBarRoutes = BottomBarTab.entries.map { it.toRoute()::class }.toSet()
private val initialFlow = AuthFlow()
