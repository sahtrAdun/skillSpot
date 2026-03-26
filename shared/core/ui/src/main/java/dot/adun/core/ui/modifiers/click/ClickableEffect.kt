package dot.adun.core.ui.modifiers.click

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
        label = "clickEffect"
    )

    this
        .graphicsLayer {
            this.scaleX = scale
            this.scaleY = scale
        }
        .click(clickable)
}

fun Modifier.awaitToClick(
    clickable: Clickable,
    awaitTo: () -> Flow<Boolean>,
    waitForCondition: Boolean = true,
): Modifier = composed {
    val isConditionMet = remember { mutableStateOf(false) }
    val isClickPending = remember { mutableStateOf(false) }

    LaunchedEffect(awaitTo) {
        awaitTo().collect { condition ->
            isConditionMet.value = condition

            if (condition && isClickPending.value) {
                isClickPending.value = false
                if (clickable.enabled) {
                    clickable.onClick()
                }
            }
        }
    }

    Modifier
        .pointerInput(clickable) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent(pass = PointerEventPass.Initial)

                    if (event.changes.any { it.pressed }) {
                        val shouldWait = waitForCondition && !isConditionMet.value

                        if (shouldWait) {
                            isClickPending.value = true
                        } else {
                            if (clickable.enabled) {
                                clickable.onClick()
                            }
                        }
                    }
                }
            }
        }
        .click(
            clickable = clickable.copy(
                onClick = {},
                onLongClick = {}
            )
        )
}
