package dot.adun.core.ui.modifiers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.sin

enum class EffectType(val duration: Long) {
    Scale(250L), Highlight(900L), Shake(450L)
}

fun Modifier.effect(
    effect: EffectType,
    cooldown: Long = effect.duration,
    trigger: () -> Boolean = { true },
): Modifier = composed {
    var isAnimating by remember { mutableStateOf(false) }
    var lastTriggerTime by remember { mutableLongStateOf(0L) }
    val triggerValue by rememberUpdatedState(trigger())

    LaunchedEffect(Unit) {
        snapshotFlow { triggerValue }
            .collect { shouldTrigger ->
                val currentTime = System.currentTimeMillis()

                if (shouldTrigger && !isAnimating && currentTime - lastTriggerTime >= cooldown) {
                    lastTriggerTime = currentTime
                    isAnimating = true
                    delay(effect.duration)
                    isAnimating = false
                }
            }
    }

    this.then(
        when (effect) {
            EffectType.Scale -> {
                val scale by animateFloatAsState(
                    targetValue = if (isAnimating) 0.98f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    label = "itemEffect"
                )

                Modifier.graphicsLayer {
                    this.scaleY = scale
                    this.scaleX = scale
                }
            }
            EffectType.Highlight -> Modifier.shimmer(isAnimating)
            EffectType.Shake -> {
                val infiniteTransition = rememberInfiniteTransition()
                val shake by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = effect.duration.toInt(),
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                val translationX = if (isAnimating) {
                    val amplitude = 14f
                    val frequency = 7f
                    amplitude * sin(shake * frequency * PI).toFloat()
                } else {
                    0f
                }

                Modifier.graphicsLayer {
                    this.translationX = translationX
                }
            }
        }
    )
}
