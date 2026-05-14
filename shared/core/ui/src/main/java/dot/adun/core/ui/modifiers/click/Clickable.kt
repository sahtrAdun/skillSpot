package dot.adun.core.ui.modifiers.click

import androidx.compose.foundation.Indication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Clickable(
    val onClick: () -> Unit,
    val onLongClick: (() -> Unit)? = null,
    val onDoubleClick: (() -> Unit)? = null,
    val enabled: Boolean = true,
    val indicationEnabled: Boolean = true,
    val indication: Indication? = null,
    val interactionSource: MutableInteractionSource? = null,
) {
    companion object {
        val none: Clickable = Clickable({})

        fun of(onClick: () -> Unit): Clickable {
            return Clickable(onClick = onClick)
        }

        @Composable
        fun of(
            interactionSource: MutableInteractionSource = ClickableDefaults.interactionSource(),
            indication: Indication = ClickableDefaults.defaultIndication(),
            onClick: () -> Unit
        ): Clickable {
            return Clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = indication
            )
        }
    }
}

object ClickableDefaults {
    @Composable
    fun interactionSource(): MutableInteractionSource {
        return remember { MutableInteractionSource() }
    }
    fun defaultIndication(
        radius: Dp? = null,
        bounded: Boolean = true,
    ): Indication = ripple(
        bounded = bounded,
        radius = radius ?: Dp.Unspecified
    )
}

fun Modifier.click(
    clickable: Clickable
): Modifier = composed {
    when {
        !clickable.indicationEnabled -> Modifier
            .combinedClickable(
                enabled = clickable.enabled,
                onClick = clickable.onClick,
                onLongClick = clickable.onLongClick,
                onDoubleClick = clickable.onDoubleClick,
                interactionSource = null,
                indication = null
            )
        clickable.indication != null -> Modifier
            .combinedClickable(
                enabled = clickable.enabled,
                onClick = clickable.onClick,
                onLongClick = clickable.onLongClick,
                onDoubleClick = clickable.onDoubleClick,
                indication = clickable.indication,
                interactionSource = clickable.interactionSource
                    ?: ClickableDefaults.interactionSource(),
            )
        else -> Modifier
            .combinedClickable(
                enabled = clickable.enabled,
                onClick = clickable.onClick,
                onLongClick = clickable.onLongClick,
                onDoubleClick = clickable.onDoubleClick,
                indication = ClickableDefaults.defaultIndication(),
                interactionSource = ClickableDefaults.interactionSource(),
            )
    }
}
