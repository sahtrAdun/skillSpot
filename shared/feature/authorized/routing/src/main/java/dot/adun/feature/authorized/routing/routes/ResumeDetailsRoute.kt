package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.details.resume.ResumeDetailsScreen
import dot.adun.feature.authorized.ui.screen.details.resume.ResumeDetailsViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ResumeDetailsRoute(
    val resumeId: String
) : Route<ResumeDetailsViewModel> {
    override val id: String = "resume_details_route"

    @Composable
    override fun Screen(viewModel: ResumeDetailsViewModel) {
        ResumeDetailsScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ResumeDetailsViewModel =
        hiltViewModel<ResumeDetailsViewModel, ResumeDetailsViewModel.Factory> {
            factory: ResumeDetailsViewModel.Factory -> factory.create(resumeId)
        }
}
