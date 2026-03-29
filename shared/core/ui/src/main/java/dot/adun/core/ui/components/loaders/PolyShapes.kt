@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package dot.adun.core.ui.components.loaders

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.graphics.shapes.RoundedPolygon

object PolyShapes {
    object Defaults {
        const val SIZE = 32
    }

    enum class Polygons(val shapes: List<RoundedPolygon>) {
        Soft(
            listOf(
                MaterialShapes.Cookie4Sided,
                MaterialShapes.Cookie6Sided,
                MaterialShapes.Cookie7Sided,
                MaterialShapes.Cookie9Sided,
                MaterialShapes.Cookie12Sided
            )
        ),
        Medium(
            listOf(
                MaterialShapes.Triangle,
                MaterialShapes.Diamond,
                MaterialShapes.Square,
                MaterialShapes.ClamShell,
                MaterialShapes.Pentagon,
                MaterialShapes.Gem,
            )
        ),
        Various(
            listOf(
                MaterialShapes.Pill,
                MaterialShapes.Circle,
                MaterialShapes.Cookie12Sided,
                MaterialShapes.Puffy,
                MaterialShapes.Flower,
            )
        )
    }
}
