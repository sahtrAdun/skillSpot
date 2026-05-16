package dot.adun.core.routing.nav3

import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope

abstract class NavFlow<F : Flow, R>(
    val flow: F,
    val navFlowScope: NavFlowScope,
    val onFinish: (R) -> Unit
) : NavigationFlow {
    init {
        navFlowScope.onStart()
    }

    open fun NavFlowScope.onStart() {}

    fun content() = with(navFlowScope) {
        NavFlowScope(
            stack = stack,
            flowParent = flow.startDestination,
            entryProviderScope = entryProviderScope,
        )
            .navigationFlow()
    }
}
