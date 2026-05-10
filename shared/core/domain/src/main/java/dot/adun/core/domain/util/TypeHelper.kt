package dot.adun.core.domain.util

inline fun <reified T : Any> Any.transformTo(): T? {
    return this as? T
}
