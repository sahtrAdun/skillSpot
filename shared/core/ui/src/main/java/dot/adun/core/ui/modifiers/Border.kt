package dot.adun.core.ui.modifiers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.border(border: Border): Modifier = this.border(
    width = border.width,
    shape = border.shape,
    color = border.color
)

@Immutable
data class Border(
    val shape: Shape,
    val color: Color,
    val width: Dp = 1.dp,
)
