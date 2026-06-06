package dot.adun.core.routing

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass

open class NavFlowScope(
    val stack: NavBackStack<NavKey>,
    val flowParent: Route<*>,
    val flowState: MutableMap<Flow, Any?>,
    val flowQueue: MutableSet<Flow>,
    val entryProviderScope: EntryProviderScope<NavKey>,
) {
    fun <T : NavKey> Flow.onStart(element: T) = runBlocking(Dispatchers.Main.immediate) {
        setRoot(this@onStart)
        push(element)
        println("stack: $stack")
    }

    fun <T : NavKey> push(element: T) = runBlocking(Dispatchers.Main.immediate) {
        if (stack.last() == element) {
            return@runBlocking
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

    fun <T : NavKey> pushNew(element: T) = runBlocking(Dispatchers.Main.immediate) {
        clearStateFor(element)
        if (element is Flow) {
            flowState += (element to element.state)
            stack.add(element.startDestination)
        } else {
            stack.add(element)
        }
    }

    fun <T : NavKey> replaceCurrent(element: T) = runBlocking(Dispatchers.Main.immediate) {
        clearStateFor(stack.last())
        pushNew(element)

        val current = stack.lastIndex - 1
        stack.removeAt(current)
    }

    fun <T : NavKey> replaceAll(element: T) = runBlocking(Dispatchers.Main.immediate) {
        popToIndex(0)
        replaceCurrent(element)
    }

    fun pop() = runBlocking(Dispatchers.Main.immediate) {
        if (stack.size > 1) {
            clearStateFor(stack.last())
            stack.removeLastOrNull()
        }
    }

    fun <T : NavKey> popTo(element: T) = runBlocking(Dispatchers.Main.immediate) {
        while (stack.last() != element) { pop() }
    }

    fun popToIndex(index: Int) = runBlocking(Dispatchers.Main.immediate) {
        while (stack.lastIndex != index) { pop() }
    }

    fun <T : NavKey> popToFirst(element: T) = runBlocking(Dispatchers.Main.immediate) {
        val concurrents = stack.filter { it == element }
        if (concurrents.size > 1) {
            val index = stack.indexOf(concurrents[0])
            popToIndex(index)
        } else {
            popTo(element)
        }
    }

    fun <T : NavKey> popToLast(element: T) = runBlocking(Dispatchers.Main.immediate) {
        val concurrents = stack.filter { it == element }
        if (concurrents.size > 1) {
            val index = stack.indexOf(concurrents.last())
            popToIndex(index)
        } else {
            popTo(element)
        }
    }

    fun replaceFlow(flow: Flow) {
        clearStateFor(flow)
        popToFirst(flow.startDestination)
        replaceCurrent(flow.startDestination)
    }

    fun popToRoot() = runBlocking(Dispatchers.Main.immediate) { popToFirst(flowParent) }

    private fun <T> clearStateFor(element: T) {
        when (element) {
            is Flow -> if (flowState.containsKey(element)) {
                flowState.remove(element)
                flowQueue.remove(element)
            }
            is Route<*> -> {
                val flow = flowState
                    .map { (key, _) -> key }
                    .firstOrNull { it.startDestination == element }
                    ?: return

                flowState.remove(flow)
                flowQueue.remove(flow)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> NavFlowScope.getFlowState(flowClass: KClass<*>): T? {
    val key = flowState.keys
        .firstOrNull { flowClass.isInstance(it) }
        ?: return null

    return flowState[key] as? T
}
