package dot.adun.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import dot.adun.core.ui.theme.presets.AdunColorPresets

sealed interface AdunTheme {
    val colors: AdunColors
    val shapes: AdunShapes
    val paddings: AdunPaddings
    val typography: AdunTypography
    val presets: AdunColorPresets
}

data class AdunThemeBuilder(
    override val colors: AdunColors,
    override val shapes: AdunShapes,
    override val paddings: AdunPaddings,
    override val typography: AdunTypography,
    override val presets: AdunColorPresets
) : AdunTheme

val LocalAppTheme = staticCompositionLocalOf<AdunTheme> {
    error("AppTheme not provided")
}

val AppTheme: AdunTheme
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTheme.current

@Composable
fun AppTheme(
    isDark: Boolean? = null,
    theme: AdunTheme = buildDefaultTheme(isDark),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppTheme provides theme
    ) {
        content()
    }
}

@Composable
private fun buildDefaultTheme(isDark: Boolean?): AdunTheme {
    val colorScheme = isDark ?: isSystemInDarkTheme()
    val colors = AdunColorPresets.materialGreen().colors(colorScheme)
    val shapes = AdunShapes()
    val typography = AdunTypography.default
    val paddings = AdunPaddings.create()
    val presets = AdunColorPresets.createWith(colors)

    return remember(colorScheme) {
        AdunThemeBuilder(
            colors = colors,
            shapes = shapes,
            paddings = paddings,
            typography = typography,
            presets = presets,
        )
    }
}
