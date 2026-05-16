package dot.adun.core.routing

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

open class NavFlowScope(
    val stack: NavBackStack<NavKey>,
    val flowParent: Route<*>,
    val entryProviderScope: EntryProviderScope<NavKey>,
) {
    var flowState: Map<Flow, Any?> = emptyMap()

    fun <T : NavKey> push(element: T) {
        if (stack.last() == element) {
            replaceCurrent(element)
        } else {
            pushNew(element)
        }
    }

    fun <T : NavKey> setRoot(element: T) {
        if (element is Flow) {
            flowState += (element to element.state)
            stack.add(0, element.startDestination)
        } else {
            stack.add(0, element)
        }
    }

    fun <T : NavKey> pushNew(element: T) {
        if (element is Flow) {
            flowState += (element to element.state)
            stack.add(element.startDestination)
        } else {
            stack.add(element)
        }
    }

    fun <T : NavKey> replaceCurrent(element: T) {
        clearStateFor(stack.last())
        pushNew(element)

        val current = stack.lastIndex - 1
        stack.removeAt(current)
    }

    fun <T : NavKey> replaceAll(element: T) {
        setRoot(element)
        popToIndex(0)
    }

    fun pop() {
        if (stack.size > 1) {
            clearStateFor(stack.last())
            stack.removeLastOrNull()
        }
    }

    fun <T : NavKey> popTo(element: T) {
        while (stack.last() != element) { pop() }
    }

    fun popToIndex(index: Int) {
        while (stack.lastIndex != index) { pop() }
    }

    fun <T : NavKey> popToFirst(element: T) {
        val concurrents = stack.filter { it == element }
        if (concurrents.size > 1) {
            val index = stack.indexOf(concurrents[0])
            popToIndex(index)
        } else {
            popTo(element)
        }
    }

    fun <T : NavKey> popToLast(element: T) {
        val concurrents = stack.filter { it == element }
        if (concurrents.size > 1) {
            val index = stack.indexOf(concurrents.last())
            popToIndex(index)
        } else {
            popTo(element)
        }
    }

    fun popToRoot() = popToFirst(flowParent)

    private fun <T> clearStateFor(element: T) {
        if (element is Flow) {
            flowState -= element
        }
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified S, reified F> NavFlowScope.getFlowState(): S? {
    val key = flowState.keys.firstOrNull { it is F } ?: return null
    return flowState[key] as? S
}
