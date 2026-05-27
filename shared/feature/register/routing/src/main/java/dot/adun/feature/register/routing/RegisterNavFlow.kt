package dot.adun.feature.register.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.route
import dot.adun.feature.register.ui.screen.RegisterScreenResult

@Immutable
class RegisterNavFlow(
    scope: NavFlowScope,
    onFinish: (RegisterFlowResult) -> Unit
) : NavFlow<RegisterFlow, RegisterFlowResult>(
    flow = RegisterFlow,
    navFlowScope = scope,
    onFinish = onFinish
) {
    override fun NavFlowScope.navigationFlow() {
        route<RegisterRoute> { result ->
            when (result) {
                is RegisterScreenResult -> onRegisterScreenResult(result)
            }
        }
    }

    private fun onRegisterScreenResult(result: RegisterScreenResult) = when (result) {
        RegisterScreenResult.Finish -> onFinish(RegisterFlowResult.Finish)
        RegisterScreenResult.Success -> onFinish(RegisterFlowResult.Success)
        RegisterScreenResult.Login -> onFinish(RegisterFlowResult.Login)
    }
}