package dot.adun.feature.login.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Unique
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data object LoginFlow : Flow, Unique() {
    override val startDestination = LoginRoute()
    override val state: Any? = null
}

fun NavFlowScope.loginFlow(
    onFinish: (LoginFlowResult) -> Unit
) = LoginNavFlow(this, onFinish)
    .content()

sealed interface LoginFlowResult {
    data object Finish : LoginFlowResult
    data object Success : LoginFlowResult
    data object Register : LoginFlowResult
}
