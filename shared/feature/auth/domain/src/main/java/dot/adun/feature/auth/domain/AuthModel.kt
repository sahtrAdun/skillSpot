@file:OptIn(ExperimentalCoroutinesApi::class)

package dot.adun.feature.auth.domain

import androidx.datastore.preferences.core.booleanPreferencesKey
import dot.adun.core.domain.store.AppPreferences
import dot.adun.core.domain.util.mapUntilChanged
import dot.adun.feature.auth.domain.entity.AuthStatus
import dot.adun.feature.profile.domain.ProfileRepository
import dot.adun.feature.profile.domain.entity.Profile
import dot.adun.feature.profile.domain.entity.ProfileResult
import dot.adun.core.domain.entity.UserRole
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val appPreferences: AppPreferences,
    private val profileRepository: ProfileRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _authStatusFlow = MutableStateFlow<AuthStatus>(AuthStatus.Loading)

    val authStatusFlow: StateFlow<AuthStatus> = _authStatusFlow.asStateFlow()
    val isFirstAuth = appPreferences.observe(booleanPreferencesKey(FIRST_AUTH_KEY))
    val profile = profileRepository.profile

    init {
        scope.launch { observeAuthStatus() }
    }

    suspend fun rememberAuth() {
        appPreferences.set(booleanPreferencesKey(FIRST_AUTH_KEY), false)
    }

    suspend fun fetchProfile() {
        when (val result = profileRepository.fetchProfile()) {
            is ProfileResult.SuccessFetch -> cacheProfile(result.profile)
            else -> Unit
        }
    }

    suspend fun updateUserRole(role: UserRole) {
        val profile = profileRepository.readProfile()
            ?.copy(role = role)
            ?: return

        val result = profileRepository.updateProfile(profile)
        if (result == ProfileResult.UpdateSuccess) {
            cacheProfile(profile)
        }
    }

    suspend fun cacheProfile(profile: Profile? = null) {
        val newProfile = profile ?: readProfile() ?: return
        profileRepository.cacheProfile(newProfile)
    }

    suspend fun readProfile(): Profile? = profileRepository.readProfile()

    private suspend fun observeAuthStatus() = isFirstAuth
        .mapUntilChanged { it ?: true }
        .flatMapLatest { firstAuth ->
            if (firstAuth) {
                flowOf(AuthStatus.NotAuthenticated)
            } else {
                supabaseClient.auth.sessionStatus
                    .map { toAuthStatus(it) }
            }
        }
        .collectLatest { status ->
            _authStatusFlow.value = status
        }

    private suspend fun toAuthStatus(status: SessionStatus): AuthStatus = when (status) {
        is SessionStatus.Initializing -> AuthStatus.Loading

        is SessionStatus.Authenticated -> {
            AuthStatus.Authenticated(status.session.user)
        }

        is SessionStatus.NotAuthenticated -> {
            profileRepository.clearProfileCache()
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

private const val FIRST_AUTH_KEY = "first_auth"
