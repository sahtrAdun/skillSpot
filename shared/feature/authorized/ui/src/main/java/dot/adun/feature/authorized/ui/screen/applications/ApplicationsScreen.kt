package dot.adun.feature.authorized.ui.screen.applications

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.applications.ApplicationsLayout

@Composable
fun ApplicationsScreen(
    viewModel: ApplicationsViewModel,
) = AppScreen(viewModel) { state, intents ->
    ApplicationsLayout(
        userRole = state.userRole,
        myApplications = state.myApplications,
        incomingApplications = state.incomingApplications,
        loadState = state.loadState,
        approvingId = state.approvingId,
        intents = intents,
    )
}
