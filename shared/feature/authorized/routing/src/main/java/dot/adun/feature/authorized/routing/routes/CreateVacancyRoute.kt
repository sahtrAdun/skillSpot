package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.create.vacancy.CreateVacancyScreen
import dot.adun.feature.authorized.ui.screen.create.vacancy.CreateVacancyViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CreateVacancyRoute(
    override val id: String = "create_vacancy_route"
) : Route<CreateVacancyViewModel> {
    @Composable
    override fun Screen(viewModel: CreateVacancyViewModel) {
        CreateVacancyScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): CreateVacancyViewModel = hiltViewModel()
}
