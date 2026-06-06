package dot.adun.feature.home.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.navigateBack
import dot.adun.core.routing.nav3.route
import dot.adun.feature.authorized.routing.routes.ActiveRoute
import dot.adun.feature.authorized.routing.routes.ResumeDetailsRoute
import dot.adun.feature.authorized.routing.routes.ResumeEditRoute
import dot.adun.feature.authorized.routing.routes.VacancyDetailsRoute
import dot.adun.feature.authorized.routing.routes.VacancyEditRoute
import dot.adun.feature.authorized.ui.screen.active.ActiveScreenResult
import dot.adun.feature.authorized.ui.screen.details.resume.ResumeDetailsScreenResult
import dot.adun.feature.authorized.ui.screen.details.vacancy.VacancyDetailsScreenResult
import dot.adun.feature.home.routing.routes.HomeRoute
import dot.adun.feature.home.ui.screen.HomeScreenResult
import dot.adun.feature.profile.routing.ProfileFlow
import dot.adun.feature.profile.routing.ProfileRoute
import dot.adun.feature.profile.routing.profileFlow
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

        route<Settings> { result ->
            when (result) {
                is SettingsScreenResult -> onSettingsScreenResult(result)
            }
        }

        route<Active> { result ->
            when (result) {
                is ActiveScreenResult -> onActiveScreenResult(result)
            }
        }

        route<VacancyDetailsRoute> { result ->
            when (result) {
                is VacancyDetailsScreenResult -> onVacancyDetailsScreenResult(result)
            }
        }

        route<ResumeDetailsRoute> { result ->
            when (result) {
                is ResumeDetailsScreenResult -> onResumeDetailsScreenResult(result)
            }
        }

        route<VacancyEditRoute> { _ -> navigateBack() }
        route<ResumeEditRoute> { _ -> navigateBack() }

        profileFlow { navigateBack() }
    }

    private fun NavFlowScope.onHomeScreenResult(result: HomeScreenResult) = when (result) {
        HomeScreenResult.Finish -> onFinish(HomeFlowResult.Finish)
        HomeScreenResult.Search -> pushNew(Search())
        HomeScreenResult.Logout -> onFinish(HomeFlowResult.Logout)
        is HomeScreenResult.Details -> {
            if (result.isVacancy) push(VacancyDetailsRoute(result.id))
            else push(ResumeDetailsRoute(result.id))
        }
        is HomeScreenResult.Profile -> push(ProfileFlow.create(result.id))
    }

    private fun NavFlowScope.onSearchScreenResult(result: SearchScreenResult) = when (result) {
        else -> navigateBack()
    }

    private fun NavFlowScope.onSettingsScreenResult(result: SettingsScreenResult) = when (result) {
        else -> navigateBack()
    }

    private fun NavFlowScope.onActiveScreenResult(result: ActiveScreenResult) = when (result) {
        is ActiveScreenResult.Details -> {
            if (result.isVacancy) push(VacancyDetailsRoute(result.id))
            else push(ResumeDetailsRoute(result.id))
        }
        ActiveScreenResult.Finish -> navigateBack()
    }

    private fun NavFlowScope.onVacancyDetailsScreenResult(result: VacancyDetailsScreenResult) = when (result) {
        is VacancyDetailsScreenResult.Edit -> push(VacancyEditRoute(result.id))
        is VacancyDetailsScreenResult.OpenAuthorProfile -> push(ProfileRoute(result.profileId))
        VacancyDetailsScreenResult.Finish -> navigateBack()
    }

    private fun NavFlowScope.onResumeDetailsScreenResult(result: ResumeDetailsScreenResult) = when (result) {
        is ResumeDetailsScreenResult.Edit -> push(VacancyEditRoute(result.id))
        is ResumeDetailsScreenResult.OpenAuthorProfile -> push(ProfileRoute(result.profileId))
        ResumeDetailsScreenResult.Finish -> navigateBack()
    }
}

typealias Home = HomeRoute
typealias Search = SearchRoute
typealias Settings = SettingsRoute
typealias Active = ActiveRoute
