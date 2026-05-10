package dot.adun.core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<T>.mapUntilChanged(selector: (T) -> R): Flow<R> {
    return mapLatest(selector).distinctUntilChanged()
}
