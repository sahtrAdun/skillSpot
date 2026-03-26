package dot.adun.feature.home.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Navigation
import dot.adun.core.routing.Unique
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data object HomeFlow : Flow, Unique() {
    override val startDestination: Navigation = HomeRoute()
}

fun NavFlowScope.homeFlow(
    onFinish: (HomeFlowResult) -> Unit
) = HomeNavFlow(this, onFinish)
    .content<HomeFlow>()

sealed interface HomeFlowResult {
    data object Finish : HomeFlowResult
}
