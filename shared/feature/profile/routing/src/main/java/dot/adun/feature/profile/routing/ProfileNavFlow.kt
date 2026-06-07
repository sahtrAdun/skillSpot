package dot.adun.feature.profile.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.getFlowState
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.navigateBack
import dot.adun.core.routing.nav3.route
import dot.adun.feature.authorized.routing.routes.ResumeDetailsRoute
import dot.adun.feature.authorized.routing.routes.VacancyDetailsRoute
import dot.adun.feature.profile.ui.screen.ProfileScreenResult

@Immutable
class ProfileNavFlow(
    scope: NavFlowScope,
    onFinish: (ProfileFlowResult) -> Unit
) : NavFlow<ProfileFlow, ProfileFlowResult>(
    flow = scope.profile(),
    navFlowScope = scope,
    onFinish = onFinish
) {
    override fun NavFlowScope.navigationFlow() {
        route<ProfileRoute> { result ->
            when (result) {
                is ProfileScreenResult -> onProfileScreenResult(result)
            }
        }
    }

    private fun NavFlowScope.onProfileScreenResult(result: ProfileScreenResult) = when (result) {
        ProfileScreenResult.Finish -> navigateBack()
        ProfileScreenResult.Logout -> onFinish(ProfileFlowResult.Logout)
        is ProfileScreenResult.Details -> {
            if (result.isVacancy) push(VacancyDetailsRoute(result.id))
            else push(ResumeDetailsRoute(result.id))
        }
        is ProfileScreenResult.OtherProfile -> push(ProfileRoute(result.profileId))
    }
}

private fun NavFlowScope.profile() = ProfileFlow(
    state = getFlowState(ProfileFlow::class) ?: ProfileFlowState("no-user-id")
)
