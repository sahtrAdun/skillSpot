package dot.adun.core.routing

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

open class NavFlowScope(
    val stack: NavBackStack<NavKey>,
    val parentStack: NavBackStack<NavKey>? = null,
    val entryProviderScope: EntryProviderScope<NavKey>
) {
    fun <T : NavKey> pushNew(element: T) = stack.add(element)

    fun <T : NavKey> replaceCurrent(element: T) {
        pushNew(element)
        stack.removeAt(stack.lastIndex - 1)
    }

    fun <T : NavKey> replaceFlow(element: T) {
        val targetStack = parentStack ?: stack
        targetStack.add(element)
        if (targetStack.size > 1) {
            targetStack.removeAt(targetStack.lastIndex - 1)
        }
    }

    fun <T : NavKey> popTo(element: T) {
        while (stack.last() != element) {
            stack.removeLastOrNull()
        }
    }

    fun popToIndex(index: Int) {
        while (stack.lastIndex != index) {
            stack.removeLastOrNull()
        }
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

    fun popToRoot() = popToFirst(stack[0])
}

