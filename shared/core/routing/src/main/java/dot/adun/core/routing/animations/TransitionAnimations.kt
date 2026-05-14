package dot.adun.core.routing.animations

import androidx.compose.animation.core.tween
import dot.adun.core.routing.nav3.TRANSITION_ANIMATION_DURATION

fun <T> transitionAnimationTween(duration: Int = TRANSITION_ANIMATION_DURATION) = tween<T>(
    durationMillis = duration
)
