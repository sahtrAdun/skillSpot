package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.create.resume.CreateResumeScreen
import dot.adun.feature.authorized.ui.screen.create.resume.CreateResumeViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CreateResumeRoute(
    override val id: String = "create_resume_route"
) : Route<CreateResumeViewModel> {
    @Composable
    override fun Screen(viewModel: CreateResumeViewModel) {
        CreateResumeScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): CreateResumeViewModel = hiltViewModel()
}
