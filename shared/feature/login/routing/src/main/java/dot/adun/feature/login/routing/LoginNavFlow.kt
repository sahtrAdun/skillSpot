package dot.adun.feature.login.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.route
import dot.adun.feature.login.ui.screen.LoginScreenResult

@Immutable
class LoginNavFlow(
    scope: NavFlowScope,
    onFinish: (LoginFlowResult) -> Unit
) : NavFlow<LoginFlow, LoginFlowResult>(
    flow = LoginFlow,
    navFlowScope = scope,
    onFinish = onFinish
) {
    override fun NavFlowScope.navigationFlow() {
        route<LoginRoute> { result ->
            when (result) {
                is LoginScreenResult -> onLoginScreenResult(result)
            }
        }
    }

    private fun onLoginScreenResult(result: LoginScreenResult) = when (result) {
        LoginScreenResult.Finish -> onFinish(LoginFlowResult.Finish)
        LoginScreenResult.Success -> onFinish(LoginFlowResult.Success)
        LoginScreenResult.Register -> onFinish(LoginFlowResult.Register)
    }
}
