package dot.adun.feature.authorized.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
data class PagingParams(
    val limit: Int,
    val offset: Int
)

fun buildPagingParams(
    listSize: Int,
    skipCache: Boolean = false,
    pageSize: Int = PagingState.defaultPageSize,
): PagingParams {
    val offset = if (skipCache) 0 else listSize

    return PagingParams(
        limit = pageSize,
        offset = offset
    )
}
