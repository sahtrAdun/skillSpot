package dot.adun.core.ui.theme.presets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import dot.adun.core.ui.theme.AdunColors
import dot.adun.core.ui.theme.ColorMode
import dot.adun.core.ui.theme.ColorPalette
import dot.adun.core.ui.theme.ColorTheme
import dot.adun.core.ui.theme.MaterialPalette

@Immutable
data class AdunColorPresets(
    val buttons: ButtonsColorPresets,
    val textFields: TextFieldsColorPresets,
    val loaders: LoaderColorPresets
) {
    companion object {
        @Composable
        fun createWith(colors: AdunColors): AdunColorPresets {
            return AdunColorPresets(
                buttons = ButtonsColorPresets.create(colors),
                textFields = TextFieldsColorPresets.create(colors),
                loaders = LoaderColorPresets.create(colors)
            )
        }

        @Composable
        fun materialGreen(): ColorTheme {
            val colorMode = createMaterialGreenColorMode()
            return ColorTheme(
                light = createLightMaterialGreen(colorMode),
                dark = createDarkMaterialGreen(colorMode)
            )
        }

        private fun createMaterialGreenColorMode(): ColorMode {
            val error = MaterialPalette.soft_red
            val warning = MaterialPalette.soft_amber
            val brandGreen = MaterialPalette.green_primary_light

            return ColorMode(
                text = AdunColors.Text(
                    primary = MaterialPalette.gray_500,
                    secondary = MaterialPalette.gray_400,
                    tertiary = MaterialPalette.gray_300,
                    accent = brandGreen,
                    hint = MaterialPalette.gray_400,
                    disabled = MaterialPalette.gray_300,
                    error = error,
                    warning = warning,
                    onPrimary = Color.White,
                    primaryInvert = MaterialPalette.gray_900
                ),
                border = AdunColors.Border(
                    primary = MaterialPalette.gray_300,
                    secondary = MaterialPalette.gray_200,
                    tertiary = MaterialPalette.gray_100,
                    hint = MaterialPalette.gray_400,
                    disabled = MaterialPalette.gray_200,
                    error = error.copy(alpha = 0.5f),
                    warning = warning.copy(alpha = 0.5f),
                    onPrimary = MaterialPalette.green_primary,
                    primaryInvert = Color.White
                ),
                icon = AdunColors.Icon(
                    primary = MaterialPalette.gray_500,
                    secondary = MaterialPalette.gray_400,
                    tertiary = MaterialPalette.gray_300,
                    hint = MaterialPalette.gray_400,
                    disabled = MaterialPalette.gray_200,
                    error = error,
                    warning = warning,
                    onPrimary = Color.White,
                    primaryInvert = MaterialPalette.gray_900
                ),
                control = AdunColors.Control(
                    primary = brandGreen,
                    secondary = MaterialPalette.green_primary,
                    tertiary = MaterialPalette.green_200,
                    hint = MaterialPalette.gray_300,
                    disabled = MaterialPalette.gray_400,
                    error = error,
                    warning = warning,
                    onPrimary = Color.White,
                    primaryInvert = MaterialPalette.gray_900
                ),
                layer = AdunColors.Layer(
                    background = MaterialPalette.gray_200,
                    surface = MaterialPalette.gray_100,
                    onSurface = MaterialPalette.gray_300,
                    primary = brandGreen,
                    primaryDisabled = MaterialPalette.gray_400,
                    onPrimary = Color.White,
                    primaryTranslucent = brandGreen.copy(alpha = 0.12f),
                    error = error,
                    warning = warning,
                    negative = error,
                    positive = MaterialPalette.green_primary,
                    neutral = MaterialPalette.gray_400
                )
            )
        }

        private fun createLightMaterialGreen(colorMode: ColorMode): AdunColors {
            val text = AdunColors.Text(
                primary = MaterialPalette.gray_900,
                secondary = MaterialPalette.gray_600,
                tertiary = MaterialPalette.gray_400,
                accent = MaterialPalette.green_primary,
                hint = MaterialPalette.gray_400,
                disabled = MaterialPalette.gray_300,
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                onPrimary = MaterialPalette.white,
                primaryInvert = MaterialPalette.white
            )
            val border = AdunColors.Border(
                primary = MaterialPalette.gray_200,
                secondary = MaterialPalette.gray_100,
                tertiary = MaterialPalette.gray_50,
                hint = MaterialPalette.gray_300,
                disabled = MaterialPalette.gray_100,
                error = MaterialPalette.soft_red.copy(alpha = 0.5f),
                warning = MaterialPalette.soft_amber.copy(alpha = 0.5f),
                onPrimary = MaterialPalette.green_primary,
                primaryInvert = MaterialPalette.white
            )
            val icon = AdunColors.Icon(
                primary = MaterialPalette.gray_900,
                secondary = MaterialPalette.gray_700,
                tertiary = MaterialPalette.gray_500,
                hint = MaterialPalette.gray_400,
                disabled = MaterialPalette.gray_300,
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                onPrimary = MaterialPalette.white,
                primaryInvert = MaterialPalette.white
            )
            val control = AdunColors.Control(
                primary = MaterialPalette.green_primary,
                secondary = MaterialPalette.green_primary_light,
                tertiary = MaterialPalette.green_200,
                hint = MaterialPalette.gray_200,
                disabled = MaterialPalette.gray_300,
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                onPrimary = MaterialPalette.white,
                primaryInvert = MaterialPalette.white
            )
            val layer = AdunColors.Layer(
                background = MaterialPalette.gray_150,
                surface = MaterialPalette.gray_50,
                onSurface = MaterialPalette.gray_200,
                primary = MaterialPalette.green_primary,
                primaryDisabled = MaterialPalette.gray_300,
                onPrimary = MaterialPalette.white,
                primaryTranslucent = MaterialPalette.green_primary.copy(alpha = 0.12f),
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                negative = MaterialPalette.soft_red,
                positive = MaterialPalette.green_primary,
                neutral = MaterialPalette.gray_200
            )
            return AdunColors(
                text = text,
                border = border,
                icon = icon,
                control = control,
                layer = layer,
                colorMode = colorMode
            )
        }

        private fun createDarkMaterialGreen(colorMode: ColorMode): AdunColors {
            val text = AdunColors.Text(
                primary = MaterialPalette.gray_100,
                secondary = ColorPalette.gray_500,
                tertiary = ColorPalette.gray_700,
                accent = MaterialPalette.green_primary_light,
                hint = ColorPalette.gray_600,
                disabled = ColorPalette.gray_700,
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                onPrimary = MaterialPalette.gray_100,
                primaryInvert = MaterialPalette.gray_900
            )
            val border = AdunColors.Border(
                primary = MaterialPalette.dark_border,
                secondary = ColorPalette.gray_800.copy(alpha = 75f),
                tertiary = ColorPalette.gray_900.copy(alpha = 75f),
                hint = MaterialPalette.gray_700,
                disabled = MaterialPalette.gray_800,
                error = MaterialPalette.soft_red.copy(alpha = 0.5f),
                warning = MaterialPalette.soft_amber.copy(alpha = 0.5f),
                onPrimary = MaterialPalette.green_primary_light,
                primaryInvert = MaterialPalette.gray_900
            )
            val icon = AdunColors.Icon(
                primary = MaterialPalette.gray_100,
                secondary = MaterialPalette.gray_400,
                tertiary = MaterialPalette.gray_600,
                hint = MaterialPalette.gray_500,
                disabled = MaterialPalette.gray_700,
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                onPrimary = MaterialPalette.green_900,
                primaryInvert = MaterialPalette.gray_900
            )
            val control = AdunColors.Control(
                primary = MaterialPalette.green_primary_light,
                secondary = MaterialPalette.green_primary,
                tertiary = MaterialPalette.green_800,
                hint = MaterialPalette.gray_800,
                disabled = MaterialPalette.gray_700,
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                onPrimary = Color.White,
                primaryInvert = MaterialPalette.gray_900
            )
            val layer = AdunColors.Layer(
                background = MaterialPalette.dark_bg,
                surface = MaterialPalette.dark_surface,
                onSurface = MaterialPalette.dark_border,
                primary = MaterialPalette.green_primary_light,
                primaryDisabled = MaterialPalette.green_primary,
                onPrimary = Color.White,
                primaryTranslucent = MaterialPalette.green_primary_light.copy(alpha = 0.12f),
                error = MaterialPalette.soft_red,
                warning = MaterialPalette.soft_amber,
                negative = MaterialPalette.soft_red,
                positive = MaterialPalette.green_primary_light,
                neutral = MaterialPalette.dark_border
            )
            return AdunColors(
                text = text,
                border = border,
                icon = icon,
                control = control,
                layer = layer,
                colorMode = colorMode
            )
        }
    }
}

val disabledColor = ColorPalette.gray_500.copy(alpha = 0.25f)

fun Color.disable(other: Color = disabledColor): Color {
    return other
        .compositeOver(this)
}
