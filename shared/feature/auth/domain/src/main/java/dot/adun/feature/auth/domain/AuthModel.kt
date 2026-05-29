package dot.adun.feature.auth.domain

import dot.adun.feature.auth.domain.entity.AuthStatus
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthModel @Inject constructor(
    private val supabaseClient: SupabaseClient
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _authStatusFlow = MutableStateFlow<AuthStatus>(AuthStatus.Loading)
    val authStatusFlow: StateFlow<AuthStatus> = _authStatusFlow.asStateFlow()

    init {
        observeAuthStatus()
    }

    private fun observeAuthStatus() {
        scope.launch {
            supabaseClient.auth
                .sessionStatus
                .collectLatest { status ->
                    _authStatusFlow.value = when (status) {
                        is SessionStatus.Initializing -> AuthStatus.Loading

                        is SessionStatus.Authenticated -> {
                            AuthStatus.Authenticated(status.session.user)
                        }

                        is SessionStatus.NotAuthenticated -> {
                            AuthStatus.NotAuthenticated
                        }

                        is SessionStatus.RefreshFailure -> {
                            if (_authStatusFlow.value is AuthStatus.Authenticated) {
                                _authStatusFlow.value
                            } else {
                                AuthStatus.NotAuthenticated
                            }
                        }
                    }
                }
        }
    }

    fun getCurrentStatus(): AuthStatus = _authStatusFlow.value
}
