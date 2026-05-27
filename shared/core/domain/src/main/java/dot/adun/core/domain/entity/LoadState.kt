package dot.adun.core.domain.entity

import dot.adun.core.domain.entity.error.AppError
import javax.annotation.concurrent.Immutable

@Immutable
sealed interface LoadState {
    data object Done : LoadState
    data object NotStarted : LoadState
    data object Loading : LoadState

    @Immutable
    data class Error(val error: AppError) : LoadState
}
