package dot.adun.feature.home.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Unique
import dot.adun.feature.home.routing.routes.HomeRoute
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data object HomeFlow : Flow, Unique() {
    override val startDestination = HomeRoute()
    override val state: Any? = null
}

fun NavFlowScope.homeFlow(
    onFinish: (HomeFlowResult) -> Unit
) = HomeNavFlow(this, onFinish)
    .content()

sealed interface HomeFlowResult {
    data object Finish : HomeFlowResult
    data object Logout : HomeFlowResult
}
