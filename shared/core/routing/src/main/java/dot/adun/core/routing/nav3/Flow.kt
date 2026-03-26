package dot.adun.core.routing.nav3

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import dot.adun.core.routing.Flow
import dot.adun.core.routing.NavFlowScope

abstract class NavFlow<F : Flow, R>(
    val flow: F,
    val scope: NavFlowScope,
    val onFinish: (R) -> Unit
) : NavigationFlow {
    val startDestination = flow.startDestination

    inline fun <reified E : F> content() = with(scope) {
        entryProviderScope.entry<E>(
            clazzContentKey = { it.id }
        ) {
            val innerStack = rememberNavBackStack(startDestination)

            DefaultNavDisplay(
                stack = innerStack,
                entryProvider = entryProvider {
                    NavFlowScope(
                        stack = innerStack,
                        parentStack = stack,
                        entryProviderScope = this,
                    )
                        .navigationFlow()
                },
            )
        }
    }
}
