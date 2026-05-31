package dot.adun.feature.home.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.navigateBack
import dot.adun.core.routing.nav3.route
import dot.adun.feature.authorized.routing.routes.ActiveRoute
import dot.adun.feature.authorized.ui.screen.active.ActiveScreenResult
import dot.adun.feature.home.routing.routes.HomeRoute
import dot.adun.feature.home.ui.screen.HomeScreenResult
import dot.adun.feature.search.routing.SearchRoute
import dot.adun.feature.search.ui.SearchScreenResult
import dot.adun.feature.settings.routing.SettingsRoute
import dot.adun.feature.settings.ui.screen.SettingsScreenResult

@Immutable
class HomeNavFlow(
    scope: NavFlowScope,
    onFinish: (HomeFlowResult) -> Unit
) : NavFlow<HomeFlow, HomeFlowResult>(
    flow = HomeFlow,
    navFlowScope = scope,
    onFinish = onFinish
) {
    override fun NavFlowScope.navigationFlow() {
        route<Home> { result ->
            when (result) {
                is HomeScreenResult -> onHomeScreenResult(result)
            }
        }

        route<Search> { result ->
            when (result) {
                is SearchScreenResult -> onSearchScreenResult(result)
            }
        }

        route<Active> { result ->
            when (result) {
                is ActiveScreenResult -> onActiveScreenResult(result)
            }
        }

        route<Settings> { result ->
            when (result) {
                is SettingsScreenResult -> onSettingsScreenResult(result)
            }
        }
    }

    private fun NavFlowScope.onHomeScreenResult(result: HomeScreenResult) = when (result) {
        HomeScreenResult.Finish -> onFinish(HomeFlowResult.Finish)
        HomeScreenResult.Search -> pushNew(Search())
        HomeScreenResult.Logout -> onFinish(HomeFlowResult.Logout)
    }

    private fun NavFlowScope.onSearchScreenResult(result: SearchScreenResult) = when (result) {
        else -> navigateBack()
    }

    private fun NavFlowScope.onActiveScreenResult(result: ActiveScreenResult) = when (result) {
        else -> navigateBack()
    }

    private fun NavFlowScope.onSettingsScreenResult(result: SettingsScreenResult) = when (result) {
        else -> navigateBack()
    }
}

typealias Home = HomeRoute
typealias Search = SearchRoute
typealias Settings = SettingsRoute
typealias Active = ActiveRoute
