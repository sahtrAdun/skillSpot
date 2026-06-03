@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package dot.adun.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.rememberScreenPaddings

@Composable
fun MaterialDecorator(
    decorators: List<MaterialDecorator>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        decorators.forEach { decorator ->
            MaterialShape(
                color = decorator.color,
                shape = decorator.shape,
                size = decorator.size,
                alignment = decorator.alignment
            )
        }
        content()
    }
}

@Composable
fun BoxScope.MaterialShape(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.layer.surface,
    shape: RoundedPolygon = MaterialShapes.Cookie12Sided,
    size: MaterialDecorator.Size = MaterialDecorator.Size.Medium,
    alignment: Alignment = Alignment.TopStart,
) {
    val paddings = rememberScreenPaddings()
    val offset = remember(alignment, size) {
        relatedOffset(
            alignment = alignment,
            size = size.value,
            paddings = paddings
        )
    }

    Box(
        modifier = modifier
            .align(alignment)
            .offset(offset.x, offset.y)
            .aspectRatio(1f)
            .requiredSize(size.value)
            .surface(
                color = color,
                shape = shape.toShape()
            )
    )
}

private fun relatedOffset(
    alignment: Alignment,
    size: Dp,
    paddings: DpOffset
) : DpOffset {
    val half = size / 2
    val (bottom, top) = paddings.x to paddings.y

    return when (alignment) {
        Alignment.TopStart -> DpOffset(x = -half, y = -half - top)
        Alignment.TopEnd -> DpOffset(x = half, y = -half - top)
        Alignment.TopCenter -> DpOffset(x = 0.dp, y = -half - top)

        Alignment.BottomStart -> DpOffset(x = -half, y = half + bottom)
        Alignment.BottomEnd -> DpOffset(x = half, y = half + bottom)
        Alignment.BottomCenter -> DpOffset(x = 0.dp, y = half + bottom)

        Alignment.CenterStart -> DpOffset(x = -half, y = 0.dp)
        Alignment.CenterEnd -> DpOffset(x = half, y = 0.dp)
        Alignment.Center -> DpOffset(x = 0.dp, y = 0.dp)
        else -> DpOffset.Zero
    }
}
