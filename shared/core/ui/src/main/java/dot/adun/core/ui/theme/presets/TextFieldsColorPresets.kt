package dot.adun.core.ui.theme.presets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.theme.AdunColors

@Immutable
data class TextFieldsColorPresets(
    val common: AdunTextFieldColors,
    val accent: AdunTextFieldColors
) {
    companion object {
        @Composable
        fun create(colors: AdunColors): TextFieldsColorPresets {
            return TextFieldsColorPresets(
                common = AdunTextFieldColors(
                    cursor = colors.text.accent,
                    // common colors
                    background = colors.layer.surface,
                    text = colors.text.secondary,
                    border = colors.border.secondary,
                    // focused colors
                    backgroundFocused = colors.layer.onSurface,
                    textFocused = colors.text.primary,
                    borderFocused = colors.border.primary,
                    // disabled colors
                    backgroundDisabled = colors.layer.surface,
                    textDisabled = colors.text.disabled,
                    borderDisabled = colors.border.disabled,
                    // error colors
                    backgroundError = colors.layer.error.copy(alpha = 0.15f),
                    textError = colors.text.error,
                    borderError = colors.border.error
                ),
                accent = AdunTextFieldColors(
                    cursor = colors.text.accent,
                    // common colors
                    background = colors.layer.surface,
                    text = colors.text.secondary,
                    border = colors.border.secondary,
                    // focused colors
                    backgroundFocused = colors.layer.primaryTranslucent,
                    textFocused = colors.text.accent,
                    borderFocused = colors.border.onPrimary,
                    // disabled colors
                    backgroundDisabled = colors.layer.surface,
                    textDisabled = colors.text.disabled,
                    borderDisabled = colors.border.disabled,
                    // error colors
                    backgroundError = colors.layer.error.copy(alpha = 0.5f),
                    textError = colors.text.error,
                    borderError = colors.border.error
                )
            )
        }

        @Composable
        fun rememberTextFieldColors(
            data: TextFieldData,
            preset : AdunTextFieldColors,
            isFocused: Boolean
        ): AdunTextFieldColor {
            return remember(data.error, data.enabled, preset, isFocused) {
                val background = when {
                    data.error != null -> preset.backgroundError
                    !data.enabled -> preset.backgroundDisabled
                    isFocused -> preset.backgroundFocused
                    else  -> preset.background
                }
                val text = when {
                    data.error != null -> preset.textError
                    !data.enabled -> preset.textDisabled
                    isFocused -> preset.textFocused
                    else  -> preset.text
                }
                val border = when {
                    data.error != null -> preset.borderError
                    !data.enabled -> preset.borderDisabled
                    isFocused -> preset.borderFocused
                    else  -> preset.border
                }

                AdunTextFieldColor(
                    cursor = preset.cursor,
                    background = background,
                    text = text,
                    border = border
                )
            }
        }
    }

    @Immutable
    data class AdunTextFieldColors(
        val cursor: Color,
        // common colors
        val background: Color,
        val text: Color,
        val border: Color,
        // focused colors
        val backgroundFocused: Color,
        val textFocused: Color,
        val borderFocused: Color,
        // disabled colors
        val backgroundDisabled: Color,
        val textDisabled: Color,
        val borderDisabled: Color,
        // error colors
        val backgroundError: Color,
        val textError: Color,
        val borderError: Color,
    )

    @Immutable
    data class AdunTextFieldColor(
        val cursor: Color,
        val background: Color,
        val text: Color,
        val border: Color,
    )
}
