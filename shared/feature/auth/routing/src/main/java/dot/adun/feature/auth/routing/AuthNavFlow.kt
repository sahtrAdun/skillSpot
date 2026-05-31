package dot.adun.feature.auth.routing

import androidx.compose.runtime.Immutable
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.getFlowState
import dot.adun.core.routing.nav3.NavFlow
import dot.adun.core.routing.nav3.navigateBack
import dot.adun.core.routing.nav3.route
import dot.adun.feature.auth.routing.AuthFlowState.Routes.Default
import dot.adun.feature.auth.routing.AuthFlowState.Routes.Login
import dot.adun.feature.auth.routing.AuthFlowState.Routes.Register
import dot.adun.feature.auth.ui.screen.AuthScreenResult
import dot.adun.feature.login.routing.LoginFlow
import dot.adun.feature.login.routing.LoginFlowResult
import dot.adun.feature.login.routing.loginFlow
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
        when (flow.state.route) {
            Default -> Unit
            Login -> flow.onStart(LoginFlow)
            Register -> flow.onStart(RegisterFlow)
        }
    }

    override fun NavFlowScope.navigationFlow() {
        route<AuthRoute> { result ->
            when (result) {
                is AuthScreenResult -> onAuthScreenResult(result)
            }
        }

        registrationFlow { result -> onRegistrationFlowResult(result) }
        loginFlow { result -> onLoginFlowResult(result) }
    }

    private fun NavFlowScope.onAuthScreenResult(result: AuthScreenResult) {
        when (result) {
            AuthScreenResult.Finish -> onFinish(AuthFlowResult.Finish)
            AuthScreenResult.Login -> push(LoginFlow)
            AuthScreenResult.Register -> push(RegisterFlow)
            AuthScreenResult.Authorized -> onFinish(AuthFlowResult.AuthorizationSuccess)
        }
    }

    private fun NavFlowScope.onRegistrationFlowResult(result: RegisterFlowResult) {
        when (result) {
            RegisterFlowResult.Finish -> navigateBack()
            RegisterFlowResult.Login -> replaceCurrent(LoginFlow)
            RegisterFlowResult.Success -> onFinish(AuthFlowResult.AuthorizationSuccess)
        }
    }

    private fun NavFlowScope.onLoginFlowResult(result: LoginFlowResult) {
        when (result) {
            LoginFlowResult.Finish -> navigateBack()
            LoginFlowResult.Register -> replaceCurrent(RegisterFlow)
            LoginFlowResult.Success -> onFinish(AuthFlowResult.AuthorizationSuccess)
        }
    }
}

private fun NavFlowScope.auth() = AuthFlow(
    state = getFlowState(AuthFlow::class) ?: AuthFlowState()
)
