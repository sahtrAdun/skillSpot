package dot.adun.feature.auth.domain.entity

import io.github.jan.supabase.auth.user.UserInfo
import javax.annotation.concurrent.Immutable

sealed interface AuthStatus {
    data object Loading : AuthStatus

    @Immutable
    data class Authenticated(val session: UserInfo?) : AuthStatus

    data object NotAuthenticated : AuthStatus
}
