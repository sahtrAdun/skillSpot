@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package dot.adun.core.ui.entity

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon
import dot.adun.core.ui.entity.MaterialDecorator.Size
import dot.adun.core.ui.theme.AppTheme

@Immutable
data class MaterialDecorator(
    val color: Color,
    val shape: RoundedPolygon,
    val size: Size,
    val alignment: Alignment,
) {
    @Immutable
    sealed class Size(val value: Dp) {
        data object Small : Size(128.dp)
        data object Medium : Size(256.dp)
        data object Large : Size(384.dp)

        @Immutable
        data class Custom(val size: Dp) : Size(size)
    }
}

@Composable
fun primaryMaterialDecorator(
    shape: RoundedPolygon = MaterialShapes.Cookie12Sided,
    size: Size = Size.Medium,
    alignment: Alignment = Alignment.TopStart,
): MaterialDecorator = MaterialDecorator(
    color = AppTheme.colors.layer.primary,
    size = size,
    shape = shape,
    alignment = alignment
)

@Composable
fun backgroundMaterialDecorator(
    shape: RoundedPolygon = MaterialShapes.Cookie12Sided,
    size: Size = Size.Medium,
    alignment: Alignment = Alignment.TopStart,
): MaterialDecorator = MaterialDecorator(
    color = AppTheme.colors.layer.background,
    size = size,
    shape = shape,
    alignment = alignment
)

@Composable
fun surfaceMaterialDecorator(
    shape: RoundedPolygon = MaterialShapes.Cookie12Sided,
    size: Size = Size.Medium,
    alignment: Alignment = Alignment.TopStart,
): MaterialDecorator = MaterialDecorator(
    color = AppTheme.colors.layer.surface,
    size = size,
    shape = shape,
    alignment = alignment
)

@Composable
fun materialDecorator(
    color: Color = AppTheme.colors.layer.onSurface,
    shape: RoundedPolygon = MaterialShapes.Cookie12Sided,
    size: Size = Size.Medium,
    alignment: Alignment = Alignment.TopStart,
): MaterialDecorator = MaterialDecorator(
    color = color,
    size = size,
    shape = shape,
    alignment = alignment
)
