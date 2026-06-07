package dot.adun.feature.authorized.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
data class PagingState<V>(
    val data: List<V>,
    val hasMore: Boolean,
    val pageSize: Int = defaultPageSize,
) {
    companion object {
        fun <T> empty(): PagingState<T> = PagingState(emptyList(), false)
        val defaultPageSize: Int get() = 20
        fun hasMore(
            dataSize: Int,
            pageSize: Int = defaultPageSize
        ): Boolean = dataSize == pageSize
    }
}

fun <T> PagingState<T>.combine(newData: List<T>): PagingState<T> {
    val hasMore = newData.size == pageSize

    return copy(
        data = data + newData,
        hasMore = hasMore
    )
}
