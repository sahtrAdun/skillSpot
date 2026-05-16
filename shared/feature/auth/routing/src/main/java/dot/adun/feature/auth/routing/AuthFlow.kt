package dot.adun.feature.auth.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Unique
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AuthFlow(
    override val state: AuthFlowState = AuthFlowState()
) : Flow, Unique() {
    override val startDestination = AuthRoute()
}

fun NavFlowScope.authFlow(
    onFinish: (AuthFlowResult) -> Unit
) = AuthNavFlow(this, onFinish)
    .content()

@Immutable
@Serializable
data class AuthFlowState(
    val route: Routes = Routes.Default
) {
    enum class Routes {
        Default, Login, Register
    }
}

sealed interface AuthFlowResult {
    data object Finish : AuthFlowResult
    data object AuthorizationSuccess : AuthFlowResult
}
