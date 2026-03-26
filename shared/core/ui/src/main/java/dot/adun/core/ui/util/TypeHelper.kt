package dot.adun.core.ui.util

inline fun <reified T : Any> Any.to(): T? {
    return if (this is T) (this as T) else null
}
