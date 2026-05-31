package dot.adun.core.data

import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T, R> apiRequest(
    onSuccess: (T) -> R,
    onEmptyOrNull: () -> R,
    fetchData: suspend () -> T?,
): R = withContext(Dispatchers.IO) {
    val data = fetchData()
    when {
        data == null || (data is List<*> && data.isEmpty()) -> onEmptyOrNull()
        else -> onSuccess(data)
    }
}

suspend fun <R> apiExecute(
    onSuccess: () -> R,
    onError: () -> R,
    execute: suspend () -> Unit
): R = withContext(Dispatchers.IO) {
    try {
        execute()
        onSuccess()
    } catch (_: Exception) {
        onError()
    }
}

suspend fun PostgrestQueryBuilder.selectFilter(
    columns: Columns,
    filter: PostgrestFilterBuilder.() -> Unit
) = this.select(
    columns = columns
) {
    filter { filter() }
}

suspend fun PostgrestQueryBuilder.selectFilter(
    filter: PostgrestFilterBuilder.() -> Unit
) = this.select {
    filter { filter() }
}
