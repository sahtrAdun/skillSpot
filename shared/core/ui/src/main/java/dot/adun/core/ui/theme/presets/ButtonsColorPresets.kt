package dot.adun.core.ui.theme.presets

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import dot.adun.core.ui.theme.AdunColors
import dot.adun.core.ui.theme.ColorPalette

@Immutable
data class ButtonsColorPresets(
    val icon: IconButton,
    val common: CommonButton
) {
    companion object {
        @Composable
        fun create(colors: AdunColors): ButtonsColorPresets {
            return ButtonsColorPresets(
                icon = IconButton(
                    // Акцентная кнопка (например, FAB или основной экшен)
                    primary = IconButtonColors(
                        containerColor = colors.layer.primary,
                        contentColor = colors.layer.onPrimary,
                        disabledContainerColor = colors.layer.primaryDisabled,
                        disabledContentColor = colors.text.disabled
                    ),
                    // Обычная кнопка на фоне (использует surface, чтобы чуть приподняться)
                    regular = IconButtonColors(
                        containerColor = colors.layer.surface,
                        contentColor = colors.text.primary,
                        disabledContainerColor = colors.layer.background,
                        disabledContentColor = colors.text.disabled
                    ),
                    // Мягкий акцент (Tonal style) - очень популярно в Material 3
                    secondary = IconButtonColors(
                        containerColor = colors.layer.primaryTranslucent,
                        contentColor = colors.layer.primary,
                        disabledContainerColor = colors.layer.neutral,
                        disabledContentColor = colors.text.disabled
                    ),
                    transparent = IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = colors.text.secondary, // Не такая яркая, как primary
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = colors.text.disabled
                    ),
                ),
                common = CommonButton(
                    primary = ButtonColors(
                        containerColor = colors.layer.primary,
                        contentColor = colors.layer.onPrimary,
                        disabledContainerColor = colors.layer.primaryDisabled,
                        disabledContentColor = colors.text.disabled
                    ),
                    secondary = ButtonColors(
                        containerColor = colors.layer.onSurface,
                        contentColor = colors.text.primary,
                        disabledContainerColor = colors.layer.onSurface.copy(alpha = 0.5f),
                        disabledContentColor = colors.text.disabled
                    ),
                    tertiary = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = colors.layer.primary,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = colors.text.disabled
                    ),
                    onSurface = ButtonColors(
                        containerColor = colors.layer.background,
                        contentColor = colors.text.primary,
                        disabledContainerColor = colors.layer.background.copy(alpha = 0.5f),
                        disabledContentColor = colors.text.disabled
                    ),
                    onBackground = ButtonColors(
                        containerColor = colors.layer.surface,
                        contentColor = colors.text.primary,
                        disabledContainerColor = colors.layer.surface.copy(alpha = 0.5f),
                        disabledContentColor = colors.text.disabled
                    ),
                    positive = ButtonColors(
                        containerColor = colors.layer.positive,
                        contentColor = colors.layer.onPrimary,
                        disabledContainerColor = colors.layer.primaryDisabled,
                        disabledContentColor = colors.text.disabled
                    ),
                    negative = ButtonColors(
                        containerColor = colors.layer.negative.copy(alpha = 0.1f),
                        contentColor = colors.layer.negative,
                        disabledContainerColor = colors.layer.neutral,
                        disabledContentColor = colors.text.disabled
                    )
                )
            )
        }
    }

    @Stable
    data class IconButton(
        val primary: IconButtonColors,
        val regular: IconButtonColors,
        val secondary: IconButtonColors,
        val transparent: IconButtonColors,
    )

    @Stable
    data class CommonButton(
        val primary: ButtonColors,
        val secondary: ButtonColors,
        val tertiary: ButtonColors,
        val onSurface: ButtonColors,
        val onBackground: ButtonColors,
        val negative: ButtonColors,
        val positive: ButtonColors
    )
}
