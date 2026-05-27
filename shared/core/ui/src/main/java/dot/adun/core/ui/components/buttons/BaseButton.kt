package dot.adun.core.ui.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import dot.adun.core.ui.components.loaders.Loader
import dot.adun.core.ui.components.loaders.PolyShapes
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.border
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.ClickableDefaults
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.theme.AppTheme

@Composable
fun BaseButton(
    clickable: Clickable,
    config: ButtonConfig,
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shapes.medium,
    content: @Composable () -> Unit
) {
    val containerColor = config.animatedContainerColor()
    val contentColor = config.animatedContentColor()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .minimumInteractiveComponentSize()
            .clickableEffect(
                clickable = clickable.copy(
                    enabled = config.state.enabled(),
                    interactionSource = ClickableDefaults.interactionSource(),
                    indication = ClickableDefaults.defaultIndication()
                ),
                scaleFactor = 0.97f
            )
            .heightIn(min = config.size.height)
            .background(
                color = containerColor,
                shape = shape
            )
            .then(
                Modifier
                    .border(
                        border = config.border() ?: Border(
                            color = contentColor,
                            shape = shape
                        )
                    )
                    .takeIf { config.borderVisibility.visible { false } }
                    ?: Modifier
            )
    ) {
        AnimatedContent(
            targetState = config.state,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            contentAlignment = Alignment.Center
        ) { state ->
            when (state) {
                is ButtonState.Loading -> Loader(
                    appearance = config.loaderAppearance(),
                    polygons = PolyShapes.Polygons.Soft,
                    size = config.size.loaderSize()
                )
                else -> MainContent(
                    appearance = config,
                    content = content,
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    appearance: ButtonConfig,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides appearance.animatedContentColor(),
        LocalTextStyle provides appearance.textStyle()
    ) {
        Box(
            modifier = modifier
                .padding(appearance.size.contentPadding)
        ) {
            content()
        }
    }
}

