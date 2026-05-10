package dot.adun.core.routing.nav3

import android.os.SystemClock
import androidx.compose.runtime.key
import androidx.compose.runtime.toString
import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Route

inline fun <reified R : Route<*>> NavFlowScope.route(
    metadata: Map<String, Any> = emptyMap(),
    noinline onNavigateBack: (() -> Unit)? = null,
    noinline onScreenResult: (result: Any?) -> Unit,
) {
    entryProviderScope.entry<R>(
        metadata = metadata,
        clazzContentKey = { it.id }
    ) { key ->
        with(key) {
            Content(
                onScreenResult = onScreenResult,
                onNavigateBack = onNavigateBack ?: { defaultNavigateBack() }
            )
        }
    }
}

fun NavFlowScope.defaultNavigateBack() {
    if (stack.size > 1) {
        stack.removeLastOrNull()
    } else {
        parentStack?.let { parent ->
            if (parent.size > 1) {
                parent.removeLastOrNull()
            }
        }
    }
}
