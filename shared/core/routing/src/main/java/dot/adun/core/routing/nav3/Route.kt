package dot.adun.core.routing.nav3

import dot.adun.core.routing.NavFlowScope
import dot.adun.core.routing.Route

inline fun <reified R : Route<*>> NavFlowScope.route(
    metadata: Map<String, Any> = emptyMap(),
    noinline onNavigateBack: (() -> Unit)? = null,
    noinline onScreenResult: (result: Any?) -> Unit,
) {
    entryProviderScope.entry<R>(metadata = metadata) { key ->
        with(key) {
            Content(
                onScreenResult = onScreenResult,
                onNavigateBack = onNavigateBack ?: { navigateBack() }
            )
        }
    }
}

fun NavFlowScope.navigateBack() { pop() }
