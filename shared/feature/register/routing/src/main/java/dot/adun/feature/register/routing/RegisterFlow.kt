package dot.adun.feature.register.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Unique
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data object RegisterFlow : Flow, Unique() {
    override val startDestination = RegisterRoute()
    override val state: Any? = null
}

fun NavFlowScope.registrationFlow(
    onFinish: (RegisterFlowResult) -> Unit
) = RegisterNavFlow(this, onFinish)
    .content()

sealed interface RegisterFlowResult {
    data object Finish : RegisterFlowResult
    data object Success : RegisterFlowResult
    data object Login : RegisterFlowResult
}
