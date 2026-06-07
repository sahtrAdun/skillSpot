package dot.adun.feature.profile.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Unique
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ProfileFlow(
    override val state: ProfileFlowState
) : Flow, Unique() {
    override val startDestination = ProfileRoute(state.id)

    companion object {
        fun create(id: String) = ProfileFlow(ProfileFlowState(id))
    }
}

fun NavFlowScope.profileFlow(
    onFinish: (ProfileFlowResult) -> Unit
) = ProfileNavFlow(this, onFinish)
    .content()

sealed interface ProfileFlowResult {
    data object Logout : ProfileFlowResult
}

@Immutable
@Serializable
data class ProfileFlowState(
    val id: String
)
