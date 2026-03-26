package dot.adun.core.ui.theme.presets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import dot.adun.core.ui.theme.AdunColors
import dot.adun.core.ui.theme.ColorMode
import dot.adun.core.ui.theme.ColorTheme
import dot.adun.core.ui.theme.MaterialPalette

@Immutable
data class AdunColorPresets(
    val buttons: ButtonsColorPresets,
    val textFields: TextFieldsColorPresets
) {
    companion object {
        @Composable
        fun createWith(colors: AdunColors): AdunColorPresets {
            return AdunColorPresets(
                buttons = ButtonsColorPresets.create(colors),
                textFields = TextFieldsColorPresets.create(colors)
            )
        }


        @Composable
        fun materialGreen(): ColorTheme {
            val colorMode = ColorMode(
                text = AdunColors.Text(
                    primary = Color.Unspecified,
                    secondary = Color.Unspecified,
                    tertiary = Color.Unspecified,
                    hint = MaterialPalette.gray_400,
                    disabled = MaterialPalette.gray_300,
                    error = MaterialPalette.soft_red,
                    warning = MaterialPalette.soft_amber,
                    onPrimary = MaterialPalette.white,
                    primaryInvert = Color.Unspecified,
                    accent = MaterialPalette.green_primary_light
                ),
                border = AdunColors.Border(
                    primary = Color.Unspecified,
                    secondary = Color.Unspecified,
                    tertiary = Color.Unspecified,
                    hint = MaterialPalette.gray_600,
                    disabled = MaterialPalette.gray_100,
                    error = MaterialPalette.soft_red.copy(alpha = 0.5f),
                    warning = MaterialPalette.soft_amber.copy(alpha = 0.5f),
                    onPrimary = MaterialPalette.green_primary_light,
                    primaryInvert = Color.Unspecified
                ),
                icon = AdunColors.Icon(
                    primary = Color.Unspecified,
                    secondary = Color.Unspecified,
                    tertiary = Color.Unspecified,
                    hint = MaterialPalette.gray_400,
                    disabled = MaterialPalette.gray_300,
                    error = MaterialPalette.soft_red,
                    warning = MaterialPalette.soft_amber,
                    onPrimary = MaterialPalette.white,
                    primaryInvert = Color.Unspecified
                ),
                control = AdunColors.Control(
                    primary = MaterialPalette.green_primary,
                    secondary = MaterialPalette.green_primary_light,
                    tertiary = MaterialPalette.green_200,
                    hint = MaterialPalette.gray_200,
                    disabled = MaterialPalette.gray_300,
                    error = MaterialPalette.soft_red,
                    warning = MaterialPalette.soft_amber,
                    onPrimary = MaterialPalette.white,
                    primaryInvert = Color.Unspecified
                ),
                layer = AdunColors.Layer(
                    background = Color.Unspecified,
                    surface = Color.Unspecified,
                    onSurface = Color.Unspecified,
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
            )

            return ColorTheme(
                light = AdunColors(
                    text = AdunColors.Text(
                        primary = MaterialPalette.gray_900,
                        secondary = MaterialPalette.gray_600,
                        tertiary = MaterialPalette.gray_400,
                        hint = colorMode.text.hint,
                        disabled = colorMode.text.disabled,
                        error = colorMode.text.error,
                        warning = colorMode.text.warning,
                        onPrimary = colorMode.text.onPrimary,
                        primaryInvert = MaterialPalette.white,
                        accent = MaterialPalette.green_primary
                    ),
                    border = AdunColors.Border(
                        primary = MaterialPalette.gray_200,
                        secondary = MaterialPalette.gray_100,
                        tertiary = MaterialPalette.gray_50,
                        hint = colorMode.border.hint,
                        disabled = colorMode.border.disabled,
                        error = colorMode.border.error,
                        warning = colorMode.border.warning,
                        onPrimary = MaterialPalette.green_primary,
                        primaryInvert = MaterialPalette.white
                    ),
                    icon = AdunColors.Icon(
                        primary = MaterialPalette.gray_900,
                        secondary = MaterialPalette.gray_700,
                        tertiary = MaterialPalette.gray_500,
                        hint = colorMode.icon.hint,
                        disabled = colorMode.icon.disabled,
                        error = colorMode.icon.error,
                        warning = colorMode.icon.warning,
                        onPrimary = colorMode.icon.onPrimary,
                        primaryInvert = MaterialPalette.white
                    ),
                    control = colorMode.control.copy(primary = MaterialPalette.green_primary),
                    layer = AdunColors.Layer(
                        background = MaterialPalette.gray_100,
                        surface = Color.White,
                        onSurface = MaterialPalette.gray_200,
                        primary = colorMode.layer.primary,
                        primaryDisabled = colorMode.layer.primaryDisabled,
                        onPrimary = colorMode.layer.onPrimary,
                        primaryTranslucent = colorMode.layer.primaryTranslucent,
                        error = colorMode.layer.error,
                        warning = colorMode.layer.warning,
                        negative = colorMode.layer.negative,
                        positive = colorMode.layer.positive,
                        neutral = MaterialPalette.gray_200
                    ),
                    colorMode = colorMode
                ),
                dark = AdunColors(
                    text = AdunColors.Text(
                        primary = MaterialPalette.gray_100,
                        secondary = MaterialPalette.gray_400,
                        tertiary = MaterialPalette.gray_500,
                        hint = colorMode.text.hint,
                        disabled = MaterialPalette.gray_700,
                        error = colorMode.text.error,
                        warning = colorMode.text.warning,
                        onPrimary = colorMode.text.onPrimary,
                        primaryInvert = MaterialPalette.gray_900,
                        accent = MaterialPalette.green_primary_light
                    ),
                    border = AdunColors.Border(
                        primary = MaterialPalette.gray_800,
                        secondary = MaterialPalette.gray_900,
                        tertiary = MaterialPalette.black_pure,
                        hint = MaterialPalette.gray_400.copy(alpha = 0.25f),
                        disabled = MaterialPalette.gray_800,
                        error = colorMode.border.error,
                        warning = colorMode.border.warning,
                        onPrimary = MaterialPalette.green_primary_light,
                        primaryInvert = MaterialPalette.gray_900
                    ),
                    icon = AdunColors.Icon(
                        primary = MaterialPalette.gray_100,
                        secondary = MaterialPalette.gray_400,
                        tertiary = MaterialPalette.gray_600,
                        hint = colorMode.icon.hint,
                        disabled = MaterialPalette.gray_700,
                        error = colorMode.icon.error,
                        warning = colorMode.icon.warning,
                        onPrimary = colorMode.icon.onPrimary,
                        primaryInvert = MaterialPalette.gray_900
                    ),
                    control = colorMode.control.copy(
                        primary = MaterialPalette.green_primary_light,
                        onPrimary = MaterialPalette.green_900
                    ),
                    layer = AdunColors.Layer(
                        background = MaterialPalette.dark_bg,
                        surface = MaterialPalette.dark_surface,
                        onSurface = MaterialPalette.dark_border,
                        primary = MaterialPalette.green_primary_light,
                        primaryDisabled = MaterialPalette.green_primary,
                        onPrimary = MaterialPalette.green_900,
                        primaryTranslucent = MaterialPalette.green_primary_light.copy(alpha = 0.12f),
                        error = colorMode.layer.error,//nen
                        warning = colorMode.layer.warning,
                        negative = colorMode.layer.negative,
                        positive = MaterialPalette.green_primary_light,
                        neutral = MaterialPalette.dark_border
                    ),
                    colorMode = colorMode
                )
            )
        }
    }
}
