package dot.adun.feature.profile.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.profile.ui.screen.edit.ProfileEditScreen
import dot.adun.feature.profile.ui.screen.edit.ProfileEditViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ProfileEditRoute(
    override val id: String = "profile_edit_route"
) : Route<ProfileEditViewModel> {
    @Composable
    override fun Screen(viewModel: ProfileEditViewModel) {
        ProfileEditScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ProfileEditViewModel = hiltViewModel()
}
