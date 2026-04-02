@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package dot.adun.core.ui.components.loaders

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.VSpacer
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.preview.PreviewColumn
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.lerp

@Composable
fun Loader(
    appearance: LoaderAppearance,
    modifier: Modifier = Modifier,
    polygons: PolyShapes.Polygons = PolyShapes.Polygons.Various,
    size: Dp = PolyShapes.Defaults.SIZE.dp
) {
    AnimatedContent(appearance) { style ->
        when (style) {
            is LoaderAppearance.Solid -> Solid(
                style = style,
                polygons = polygons,
                modifier = modifier,
                size = size
            )
            is LoaderAppearance.Combined -> Combined(
                style = style,
                polygons = polygons,
                modifier = modifier,
                size = size
            )
            is LoaderAppearance.Twisted -> Twisted(
                style = style,
                polygons = polygons,
                modifier = modifier,
                size = size
            )
        }
    }
}

@Composable
private fun Solid(
    style: LoaderAppearance.Solid,
    polygons: PolyShapes.Polygons,
    modifier: Modifier = Modifier,
    size: Dp = PolyShapes.Defaults.SIZE.dp
) {
    LoadingIndicator(
        color = style.color,
        polygons = polygons.shapes,
        modifier = modifier.size(size)
    )
}

@Composable
private fun Combined(
    style: LoaderAppearance.Combined,
    polygons: PolyShapes.Polygons,
    modifier: Modifier = Modifier,
    size: Dp = PolyShapes.Defaults.SIZE.dp
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator(
            color = style.layer1,
            polygons = polygons.shapes,
            modifier = modifier.size(size)
        )

        LoadingIndicator(
            color = style.layer2,
            polygons = polygons.shapes,
            modifier = modifier.size((size.value / 1.5).dp)
        )

        LoadingIndicator(
            color = style.layer3,
            polygons = polygons.shapes,
            modifier = modifier.size((size.value / 2.5).dp)
        )
    }
}

@Composable
private fun Twisted(
    style: LoaderAppearance.Twisted,
    polygons: PolyShapes.Polygons,
    modifier: Modifier = Modifier,
    size: Dp = PolyShapes.Defaults.SIZE.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "LoaderScaleTransition")

    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0f at 0
                1f at 1000
                0f at 2000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "ScaleProgress"
    )

    val springScale by animateFloatAsState(
        targetValue = animationProgress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "SpringFactor"
    )

    val baseScale = 1f
    val smallScale = 0.5f

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val firstScale = lerp(baseScale, smallScale, springScale)
        LoadingIndicator(
            color = style.mainColor,
            polygons = polygons.shapes,
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    scaleX = firstScale
                    scaleY = firstScale
                }
        )

        val secondScale = lerp(smallScale, baseScale, springScale)
        LoadingIndicator(
            color = style.twinColor,
            polygons = polygons.shapes,
            modifier = Modifier
                .size(size)
                .blur(1.dp)
                .graphicsLayer {
                    scaleX = -secondScale
                    scaleY = secondScale

                    alpha = (1.33f - secondScale).coerceAtMost(1f)
                }
        )
    }
}

@Preview
@Composable
private fun LoadersPreviewColumn() {
    PreviewColumn {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item("primary") {
                val solid = AppTheme.presets.loaders.solid.primary
                val combined = AppTheme.presets.loaders.combined.primary
                val twisted = AppTheme.presets.loaders.twisted.primary

                PreviewBlock(
                    label = "primary",
                    solid = solid,
                    combined = combined,
                    twisted = twisted
                )
            }

            item("onPrimary") {
                val solid = AppTheme.presets.loaders.solid.onPrimary
                val combined = AppTheme.presets.loaders.combined.onPrimary
                val twisted = AppTheme.presets.loaders.twisted.onPrimary

                PreviewBlock(
                    label = "onPrimary",
                    solid = solid,
                    combined = combined,
                    twisted = twisted
                )
            }

            item("surface") {
                val solid = AppTheme.presets.loaders.solid.surface
                val combined = AppTheme.presets.loaders.combined.surface
                val twisted = AppTheme.presets.loaders.twisted.surface

                PreviewBlock(
                    label = "surface",
                    solid = solid,
                    combined = combined,
                    twisted = twisted
                )
            }

            item("background") {
                val solid = AppTheme.presets.loaders.solid.background
                val combined = AppTheme.presets.loaders.combined.background
                val twisted = AppTheme.presets.loaders.twisted.background

                PreviewBlock(
                    label = "background",
                    solid = solid,
                    combined = combined,
                    twisted = twisted
                )
            }
        }
    }
}

@Composable
private fun PreviewBlock(
    label: String,
    solid: LoaderAppearance.Solid,
    combined: LoaderAppearance.Combined,
    twisted: LoaderAppearance.Twisted
) {
    Column(
        modifier = Modifier
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Text(
            text = label,
            color = AppTheme.colors.text.primary
        )
        VSpacer(12.dp)
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = AppTheme.colors.border.primary
        )

        PreviewRow("soft") {
            Loader(
                appearance = solid,
                polygons = PolyShapes.Polygons.Soft
            )

            Loader(
                appearance = combined,
                polygons = PolyShapes.Polygons.Soft
            )

            Loader(
                appearance = twisted,
                polygons = PolyShapes.Polygons.Soft
            )
        }

        PreviewRow("medium") {
            Loader(
                appearance = solid,
                polygons = PolyShapes.Polygons.Medium
            )

            Loader(
                appearance = combined,
                polygons = PolyShapes.Polygons.Medium
            )

            Loader(
                appearance = twisted,
                polygons = PolyShapes.Polygons.Medium
            )
        }

        PreviewRow("various") {
            Loader(
                appearance = solid,
                polygons = PolyShapes.Polygons.Various
            )

            Loader(
                appearance = combined,
                polygons = PolyShapes.Polygons.Various
            )

            Loader(
                appearance = twisted,
                polygons = PolyShapes.Polygons.Various
            )
        }
    }
}

@Composable
private fun PreviewRow(
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        content()
        Text(
            text = label,
            color = AppTheme.colors.text.primary
        )
    }
}
