package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.details.resume.edit.ResumeEditScreen
import dot.adun.feature.authorized.ui.screen.details.resume.edit.ResumeEditViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ResumeEditRoute(
    val resumeId: String,
) : Route<ResumeEditViewModel> {
    override val id: String = "resume_edit_route"

    @Composable
    override fun Screen(viewModel: ResumeEditViewModel) {
        ResumeEditScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ResumeEditViewModel =
        hiltViewModel<ResumeEditViewModel, ResumeEditViewModel.Factory> {
            factory: ResumeEditViewModel.Factory -> factory.create(resumeId)
        }
}