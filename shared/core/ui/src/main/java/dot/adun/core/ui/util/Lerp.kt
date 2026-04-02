package dot.adun.core.ui.util

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}
