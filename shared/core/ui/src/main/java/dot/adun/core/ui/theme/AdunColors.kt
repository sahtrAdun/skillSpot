package dot.adun.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AdunColors(
    val text: Text,
    val border: Border,
    val icon: Icon,
    val control: Control,
    val layer: Layer,
    val colorMode: ColorMode // colors same for both color schemes
) {
    @Immutable
    data class Text(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val accent: Color,
        val hint: Color,
        val disabled: Color,
        val error: Color,
        val warning: Color,
        val onPrimary: Color,
        val primaryInvert: Color
    )

    @Immutable
    data class Border(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val hint: Color,
        val disabled: Color,
        val error: Color,
        val warning: Color,
        val onPrimary: Color,
        val primaryInvert: Color
    )

    @Immutable
    data class Icon(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val accent: Color,
        val hint: Color,
        val disabled: Color,
        val error: Color,
        val warning: Color,
        val onPrimary: Color,
        val primaryInvert: Color
    )

    @Immutable
    data class Control(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val hint: Color,
        val disabled: Color,
        val error: Color,
        val warning: Color,
        val onPrimary: Color,
        val primaryInvert: Color
    )

    @Immutable
    data class Layer(
        val background: Color,
        val surface: Color,
        val onSurface: Color,
        val primary: Color,
        val primaryDisabled: Color,
        val onPrimary: Color,
        val primaryTranslucent: Color,
        val error: Color,
        val warning: Color,
        val negative: Color,
        val positive: Color,
        val neutral: Color
    )
}

@Immutable
data class ColorMode(
    val text: AdunColors.Text,
    val border: AdunColors.Border,
    val icon: AdunColors.Icon,
    val control: AdunColors.Control,
    val layer: AdunColors.Layer,
)

@Immutable
data class ColorTheme(
    val light: AdunColors,
    val dark: AdunColors
) {
    fun colors(isDark: Boolean): AdunColors {
        return when (isDark) {
            true -> this.dark
            false -> this.light
        }
    }
}
