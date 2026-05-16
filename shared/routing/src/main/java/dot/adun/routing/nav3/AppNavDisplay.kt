package dot.adun.routing.nav3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.DefaultNavDisplay
import dot.adun.feature.auth.routing.AuthFlow
import dot.adun.feature.auth.routing.AuthFlowResult
import dot.adun.feature.auth.routing.AuthFlowState
import dot.adun.feature.auth.routing.authFlow
import dot.adun.feature.home.routing.HomeFlow
import dot.adun.feature.home.routing.HomeFlowResult
import dot.adun.feature.home.routing.homeFlow

@Composable
fun AppNavigation(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var flow by remember { mutableStateOf<Flow>(AuthFlow()) }
    val stack = rememberNavBackStack(flow.startDestination)

    DefaultNavDisplay(
        stack = stack,
        modifier = modifier,
        entryProvider = appEntryProvider(stack, flow) {
            authFlow { result ->
                when (result) {
                    AuthFlowResult.Finish -> onFinish()
                    AuthFlowResult.AuthorizationSuccess -> {
                        flow = HomeFlow
                        replaceAll(flow)
                    }
                }
            }

            homeFlow { result ->
                when (result) {
                    HomeFlowResult.Finish -> onFinish()
                    HomeFlowResult.Logout -> {
                        val state = AuthFlowState(AuthFlowState.Routes.Login)
                        flow = AuthFlow(state)
                        replaceAll(flow)
                    }
                }
            }
        }
    )
}

private fun appEntryProvider(
    backStack: NavBackStack<NavKey>,
    flow: Flow,
    content: NavFlowScope.() -> Unit
): (NavKey) -> NavEntry<NavKey> = entryProvider {
    val scope = NavFlowScope(backStack, flow.startDestination, this)
    scope.content()
}
