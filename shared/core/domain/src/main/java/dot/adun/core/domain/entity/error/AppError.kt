package dot.adun.core.domain.entity.error

import javax.annotation.concurrent.Immutable

sealed interface AppError {
    @Immutable
    data class Unknown(val throwable: Throwable) : ApiError
}
