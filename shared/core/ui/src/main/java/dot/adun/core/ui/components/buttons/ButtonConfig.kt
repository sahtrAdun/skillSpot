package dot.adun.core.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.theme.AppTheme

@Immutable
sealed interface ButtonConfig {
    val colors: ButtonColors
    val size: Size
    val state: ButtonState
    val borderVisibility: BorderVisibility

    enum class Size(
        val height: Dp,
        val contentPadding: PaddingValues
    ) {
        Small(
            height = 32.dp,
            contentPadding = PaddingValues(vertical = 6.dp, horizontal = 10.dp)
        ),
        Medium(
            height = 48.dp,
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
        ),
        Large(
            height = 56.dp,
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
        )
    }

    @Immutable
    data class Primary(
        override val colors: ButtonColors,
        override val size: Size,
        override val state: ButtonState,
        override val borderVisibility: BorderVisibility
    ) : ButtonConfig

    @Immutable
    data class Secondary(
        override val colors: ButtonColors,
        override val size: Size,
        override val state: ButtonState,
        override val borderVisibility: BorderVisibility
    ) : ButtonConfig

    @Immutable
    data class Tertiary(
        override val colors: ButtonColors,
        override val size: Size,
        override val state: ButtonState,
        override val borderVisibility: BorderVisibility
    ) : ButtonConfig

    @Immutable
    data class Surface(
        override val colors: ButtonColors,
        override val size: Size,
        override val state: ButtonState,
        override val borderVisibility: BorderVisibility
    ) : ButtonConfig

    @Immutable
    data class Background(
        override val colors: ButtonColors,
        override val size: Size,
        override val state: ButtonState,
        override val borderVisibility: BorderVisibility
    ) : ButtonConfig

    fun containerColor(): Color {
        return if (state.enabled()) colors.containerColor else colors.disabledContainerColor
    }

    @Composable
    fun animatedContainerColor(): Color {
        return animateColorAsState(
            targetValue = if (state.enabled()) colors.containerColor else colors.disabledContainerColor
        )
            .value
    }

    fun contentColor(): Color {
        return if (state.enabled()) colors.contentColor else colors.disabledContentColor
    }

    @Composable
    fun animatedContentColor(): Color {
        return animateColorAsState(
            targetValue = if (state.enabled()) colors.contentColor else colors.disabledContentColor
        )
            .value
    }

    @Composable
    fun textStyle(): TextStyle {
        return when (size) {
            Size.Small -> AppTheme.typography.body1
            Size.Medium -> AppTheme.typography.subhead3
            Size.Large -> AppTheme.typography.subhead2
        }
    }

    @Composable
    fun border(): Border? {
        return when (this) {
            is Primary,
            is Secondary,
            is Tertiary -> null
            is Surface -> Border(
                color = colors.contentColor,
                width = 0.5.dp,
                shape = AppTheme.shapes.medium
            )
            is Background -> Border(
                color = AppTheme.colors.layer.surface,
                width = 1.dp,
                shape = AppTheme.shapes.medium
            )
        }
    }

    companion object {
        @Composable
        fun primary(
            state: ButtonState = rememberButtonState(),
            colors: ButtonColors = AppTheme.presets.buttons.common.primary,
            size: Size = Size.Large
        ): ButtonConfig {
            return remember(state, colors, size) {
                Primary(
                    colors = colors,
                    size = size,
                    state = state,
                    borderVisibility = BorderVisibility.Newer
                )
            }
        }

        @Composable
        fun secondary(
            state: ButtonState = rememberButtonState(),
            colors: ButtonColors = AppTheme.presets.buttons.common.secondary,
            size: Size = Size.Large
        ): ButtonConfig {
            return remember(state, colors, size) {
                Secondary(
                    colors = colors,
                    size = size,
                    state = state,
                    borderVisibility = BorderVisibility.Newer
                )
            }
        }

        @Composable
        fun tertiary(
            state: ButtonState = rememberButtonState(),
            colors: ButtonColors = AppTheme.presets.buttons.common.tertiary,
            size: Size = Size.Large
        ): ButtonConfig {
            return remember(state, colors, size) {
                Tertiary(
                    colors = colors,
                    size = size,
                    state = state,
                    borderVisibility = BorderVisibility.Always
                )
            }
        }

        @Composable
        fun surface(
            state: ButtonState = rememberButtonState(),
            colors: ButtonColors = AppTheme.presets.buttons.common.onSurface,
            size: Size = Size.Large
        ): ButtonConfig {
            return remember(state, colors, size) {
                Surface(
                    colors = colors,
                    size = size,
                    state = state,
                    borderVisibility = BorderVisibility.Always
                )
            }
        }

        @Composable
        fun background(
            state: ButtonState = rememberButtonState(),
            colors: ButtonColors = AppTheme.presets.buttons.common.onBackground,
            size: Size = Size.Large
        ): ButtonConfig {
            return remember(state, colors, size) {
                Background(
                    colors = colors,
                    size = size,
                    state = state,
                    borderVisibility = BorderVisibility.Always
                )
            }
        }
    }
}
