package dot.adun.core.ui.util

inline fun <reified T : Any> Any.to(): T? {
    return this as? T
}
