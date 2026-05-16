package dot.adun.core.routing

interface Flow : Navigation {
    val startDestination: Route<*>
    val state: Any?
}
