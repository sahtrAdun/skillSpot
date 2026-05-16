package dot.adun.feature.auth.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.getFlowState
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.navigateBack
import dot.adun.core.routing.nav3.route
import dot.adun.feature.auth.routing.AuthFlowState.Routes.*
import dot.adun.feature.auth.ui.screen.AuthScreenResult
import dot.adun.feature.register.routing.RegisterFlow
import dot.adun.feature.register.routing.RegisterFlowResult
import dot.adun.feature.register.routing.registrationFlow

@Immutable
class AuthNavFlow(
    scope: NavFlowScope,
    onFinish: (AuthFlowResult) -> Unit
) : NavFlow<AuthFlow, AuthFlowResult>(
    flow = scope.auth(),
    navFlowScope = scope,
    onFinish = onFinish
) {
    override fun NavFlowScope.onStart() {
        if (!stack.contains(flow.startDestination)) {
            setRoot(flow.startDestination)
        }

        when (flow.state.route) {
            Default -> Unit
            Login -> TODO()
            Register -> push(RegisterFlow)
        }
    }

    override fun NavFlowScope.navigationFlow() {
        route<AuthRoute> { result ->
            when (result) {
                is AuthScreenResult -> onAuthScreenResult(result)
            }
        }

        registrationFlow { result -> onRegistrationFlowResult(result) }
    }

    private fun NavFlowScope.onAuthScreenResult(result: AuthScreenResult) {
        when (result) {
            AuthScreenResult.Finish -> onFinish(AuthFlowResult.Finish)
            AuthScreenResult.Login -> TODO()
            AuthScreenResult.Register -> push(RegisterFlow)
        }
    }

    private fun NavFlowScope.onRegistrationFlowResult(result: RegisterFlowResult) {
        when (result) {
            RegisterFlowResult.Finish -> navigateBack()
            RegisterFlowResult.Login -> TODO()
            RegisterFlowResult.Success -> onFinish(AuthFlowResult.AuthorizationSuccess)
        }
    }
}

private fun NavFlowScope.auth() = AuthFlow(
    state = getFlowState<AuthFlowState, AuthFlow>() ?: AuthFlowState()
)

