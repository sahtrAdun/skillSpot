package dot.adun.core.ui.modifiers.click

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale

@Composable
fun Modifier.clickableEffect(
    clickable: Clickable,
    scaleFactor: Float = 0.94f,
): Modifier = composed {
    val interactionSource = clickable.interactionSource ?: ClickableDefaults.interactionSource()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleFactor else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "clickEffect ${clickable.hashCode()}"
    )

    this
        /*.graphicsLayer {
            this.scaleX = scale
            this.scaleY = scale
        }*/
        .scale(scale)
        .click(clickable)
}
