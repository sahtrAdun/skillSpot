package dot.adun.feature.home.routing

import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.route
import dot.adun.feature.home.ui.HomeScreenResult
import dot.adun.feature.search.routing.SearchRoute

class HomeNavFlow(
    scope: NavFlowScope,
    onFinish: (HomeFlowResult) -> Unit
) : NavFlow<HomeFlow, HomeFlowResult>(
    flow = HomeFlow,
    scope = scope,
    onFinish = onFinish
) {
    override fun NavFlowScope.navigationFlow() {
        route<Home> { result ->
            when (result) {
                is HomeScreenResult -> onHomeScreenResult(result)
                else -> Unit
            }
        }

        route<Search> { pushNew(Search()) }
    }

    private fun NavFlowScope.onHomeScreenResult(result: HomeScreenResult) = when (result) {
        HomeScreenResult.Finish -> onFinish(HomeFlowResult.Finish)
        HomeScreenResult.Search -> pushNew(Search())
    }
}

typealias Home = HomeRoute
typealias Search = SearchRoute
