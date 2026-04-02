package dot.adun.core.ui.components.loaders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Immutable
sealed interface LoaderAppearance {
    @Immutable
    data class Solid(
        val color: Color
    ) : LoaderAppearance

    @Immutable
    data class Combined(
        val layer1: Color,
        val layer2: Color,
        val layer3: Color,
    ) : LoaderAppearance

    @Immutable
    data class Twisted(
        val mainColor: Color,
        val twinColor: Color
    ) : LoaderAppearance
}

@Composable
fun rememberSolidLoaderColors(color: Color): LoaderAppearance.Solid {
    return remember { LoaderAppearance.Solid(color) }
}

@Composable
fun rememberCombinedLoaderColors(
    layer1: Color,
    layer2: Color,
    layer3: Color,
): LoaderAppearance.Combined {
    return remember {
        LoaderAppearance.Combined(
            layer1 = layer1,
            layer2 = layer2,
            layer3 = layer3
        )
    }
}

@Composable
fun rememberTwistedLoaderColors(
    mainColor: Color,
    twinColor: Color
): LoaderAppearance.Twisted {
    return remember {
        LoaderAppearance.Twisted(
            mainColor = mainColor,
            twinColor = twinColor
        )
    }
}
