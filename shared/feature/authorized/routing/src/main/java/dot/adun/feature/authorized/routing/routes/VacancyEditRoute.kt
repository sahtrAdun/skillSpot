package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.details.vacancy.edit.VacancyEditScreen
import dot.adun.feature.authorized.ui.screen.details.vacancy.edit.VacancyEditViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class VacancyEditRoute(
    val vacancyId: String
) : Route<VacancyEditViewModel> {
    override val id: String = "vacancy_edit_route"

    @Composable
    override fun Screen(viewModel: VacancyEditViewModel) {
        VacancyEditScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): VacancyEditViewModel =
        hiltViewModel<VacancyEditViewModel, VacancyEditViewModel.Factory> {
            factory: VacancyEditViewModel.Factory -> factory.create(vacancyId)
        }
}