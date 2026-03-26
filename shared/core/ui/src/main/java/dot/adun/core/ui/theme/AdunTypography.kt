package dot.adun.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dot.adun.core.ui.R

@Immutable
data class AdunTypography(
    val headline1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 96.sp,
        lineHeight = 112.sp,
        letterSpacing = (-1.5).sp
    ),
    val headline2: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 60.sp,
        lineHeight = 72.sp,
        letterSpacing = (-0.5).sp
    ),
    val headline3: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp
    ),
    val headline4: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.25.sp
    ),
    val headline5: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    val subhead1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    val subhead2: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    val subhead3: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),

    val body1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    val body2: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    val body3: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    val caption1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    val caption2: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    ),
    val caption3: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    ),
    val caption4: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 9.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.6.sp
    ),
    val caption5: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 8.sp,
        lineHeight = 8.sp,
        letterSpacing = 0.8.sp
    )
) {
    companion object Presets {
        val simple: AdunTypography = AdunTypography().withFontFamilyAll(
            fontFamily = font(R.font.montserrat)
        )

        val default: AdunTypography = AdunTypography().withFontFamily()
    }
}

fun AdunTypography.withFontFamilyAll(fontFamily: FontFamily): AdunTypography {
    return this.copy(
        headline1 = this.headline1.copy(fontFamily = fontFamily),
        headline2 = this.headline2.copy(fontFamily = fontFamily),
        headline3 = this.headline3.copy(fontFamily = fontFamily),
        headline4 = this.headline4.copy(fontFamily = fontFamily),
        headline5 = this.headline5.copy(fontFamily = fontFamily),
        subhead1 = this.subhead1.copy(fontFamily = fontFamily),
        subhead2 = this.subhead2.copy(fontFamily = fontFamily),
        subhead3 = this.subhead3.copy(fontFamily = fontFamily),
        body1 = this.body1.copy(fontFamily = fontFamily),
        body2 = this.body2.copy(fontFamily = fontFamily),
        body3 = this.body3.copy(fontFamily = fontFamily),
        caption1 = this.caption1.copy(fontFamily = fontFamily),
        caption2 = this.caption2.copy(fontFamily = fontFamily),
        caption3 = this.caption3.copy(fontFamily = fontFamily),
        caption4 = this.caption4.copy(fontFamily = fontFamily),
        caption5 = this.caption5.copy(fontFamily = fontFamily)
    )
}

fun AdunTypography.withFontFamily(
    default: FontFamily = font(R.font.abeezee_regular),
    extrabold: FontFamily = font(R.font.benzin_extra_bold),
    bold: FontFamily = font(R.font.benzin_bold),
    semibold: FontFamily = font(R.font.benzin_semibold),
    medium: FontFamily = font(R.font.benzin_medium),
    normal: FontFamily = font(R.font.benzin_regular),
): AdunTypography {
    return this.copy(
        headline1 = this.headline1.copy(fontFamily = extrabold),
        headline2 = this.headline2.copy(fontFamily = extrabold),
        headline3 = this.headline3.copy(fontFamily = bold),
        headline4 = this.headline4.copy(fontFamily = semibold),
        headline5 = this.headline5.copy(fontFamily = normal),
        subhead1 = this.subhead1.copy(fontFamily = semibold),
        subhead2 = this.subhead2.copy(fontFamily = medium),
        subhead3 = this.subhead3.copy(fontFamily = normal),
        body1 = this.body1.copy(fontFamily = default),
        body2 = this.body2.copy(fontFamily = default),
        body3 = this.body3.copy(fontFamily = default),
        caption1 = this.caption1.copy(fontFamily = default),
        caption2 = this.caption2.copy(fontFamily = default),
        caption3 = this.caption3.copy(fontFamily = default),
        caption4 = this.caption4.copy(fontFamily = default),
        caption5 = this.caption5.copy(fontFamily = default)
    )
}

private fun font(font: Int): FontFamily = FontFamily(Font(font))
