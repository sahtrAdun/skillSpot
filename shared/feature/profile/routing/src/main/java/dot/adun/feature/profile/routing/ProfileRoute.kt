package dot.adun.feature.profile.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.profile.ui.screen.ProfileScreen
import dot.adun.feature.profile.ui.screen.ProfileViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ProfileRoute(
    val profileId: String,
) : Route<ProfileViewModel> {
    override val id: String = "profile_route"

    @Composable
    override fun Screen(viewModel: ProfileViewModel) {
        ProfileScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ProfileViewModel =
        hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory: ProfileViewModel.Factory ->
            factory.create(profileId)
        }
}
