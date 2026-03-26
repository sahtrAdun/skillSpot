package dot.adun.core.ui.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.click

fun Modifier.surface(
    color: Color,
    shape: Shape = RectangleShape,
    clickable: Clickable? = null
): Modifier = this
    .surface(
        color = color,
        shape = shape,
        padding = PaddingValues(0.dp),
        border = null,
        clickable = clickable
    )

fun Modifier.surface(
    color: Color,
    shape: Shape = RectangleShape,
    border: Border? = null,
    clickable: Clickable? = null
): Modifier = this
    .surface(
        color = color,
        shape = shape,
        padding = PaddingValues(0.dp),
        border = border,
        clickable = clickable
    )

fun Modifier.surface(
    color: Color,
    shape: Shape = RectangleShape,
    padding: Dp = 0.dp,
    border: Border? = null,
    clickable: Clickable? = null
): Modifier = this
    .surface(
        color = color,
        shape = shape,
        padding = PaddingValues(padding),
        border = border,
        clickable = clickable
    )

fun Modifier.surface(
    color: Color,
    shape: Shape = RectangleShape,
    padding: PaddingValues = PaddingValues(0.dp),
    border: Border? = null,
    clickable: Clickable? = null
): Modifier = this
    .clip(shape)
    .background(
        color = color,
        shape = shape,
    )
    .then(
        if (clickable != null) {
            Modifier.click(clickable)
        } else {
            Modifier
        }
    )
    .then(
        Modifier.takeIf { border == null } ?: Modifier.border(border!!)
    )
    .padding(padding)
