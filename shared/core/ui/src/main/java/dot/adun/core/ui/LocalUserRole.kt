package dot.adun.core.ui

import androidx.compose.runtime.staticCompositionLocalOf
import dot.adun.core.domain.entity.UserRole

val LocalUserRole = staticCompositionLocalOf<UserRole> {
    error("No UserRole provided")
}
