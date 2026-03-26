package dot.adun.routing.nav3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.DefaultNavDisplay
import dot.adun.feature.home.routing.HomeFlow
import dot.adun.feature.home.routing.homeFlow

@Composable
fun AppNavigation(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stack = rememberNavBackStack(HomeFlow)

    DefaultNavDisplay(
        stack = stack,
        entryProvider = appEntryProvider(stack) {
            homeFlow { _ -> onFinish() }
        },
        modifier = modifier
    )
}

private fun appEntryProvider(
    backStack: NavBackStack<NavKey>,
    content: NavFlowScope.() -> Unit
): (NavKey) -> NavEntry<NavKey> = entryProvider {
    val scope = NavFlowScope(backStack, null, this)
    scope.content()
}
