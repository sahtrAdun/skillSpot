package dot.adun.core.ui.components.animations

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

fun <T> Spring.low(visibilityThreshold: T? = null) = spring(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness = Spring.StiffnessLow,
    visibilityThreshold = visibilityThreshold
)

fun <T> Spring.medium(visibilityThreshold: T? = null) = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = visibilityThreshold
)

fun <T> Spring.high(visibilityThreshold: T? = null) = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMedium,
    visibilityThreshold = visibilityThreshold
)
