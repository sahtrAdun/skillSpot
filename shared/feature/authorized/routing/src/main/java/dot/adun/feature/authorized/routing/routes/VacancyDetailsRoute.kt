package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.details.resume.ResumeDetailsViewModel
import dot.adun.feature.authorized.ui.screen.details.vacancy.VacancyDetailsScreen
import dot.adun.feature.authorized.ui.screen.details.vacancy.VacancyDetailsViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class VacancyDetailsRoute(
    val vacancyId: String
) : Route<VacancyDetailsViewModel> {
    override val id: String = "vacancy_details_route"

    @Composable
    override fun Screen(viewModel: VacancyDetailsViewModel) {
        VacancyDetailsScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): VacancyDetailsViewModel =
        hiltViewModel<VacancyDetailsViewModel, VacancyDetailsViewModel.Factory> {
                factory: VacancyDetailsViewModel.Factory -> factory.create(vacancyId)
        }
}
