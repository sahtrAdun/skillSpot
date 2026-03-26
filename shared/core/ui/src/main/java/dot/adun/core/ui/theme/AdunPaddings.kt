package dot.adun.core.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AdunPaddings(
    val full: Full,
    val vertical: Vertical,
    val horizontal: Horizontal,
    val inset: Inset,
    val space: Space,

    val icons: PaddingValues = PaddingValues(6.dp),
) {
    companion object {
        fun create(): AdunPaddings {
            return AdunPaddings(
                full = Full(),
                vertical = Vertical(),
                horizontal = Horizontal(),
                space = Space(),
                inset = Inset()
            )
        }
    }

    @Immutable
    data class Full(
        val xs: PaddingValues = PaddingValues(4.dp),
        val small: PaddingValues = PaddingValues(8.dp),
        val semiRegular: PaddingValues = PaddingValues(12.dp),
        val regular: PaddingValues = PaddingValues(16.dp),
        val large: PaddingValues = PaddingValues(20.dp),
        val xl: PaddingValues = PaddingValues(24.dp),
    )

    @Immutable
    data class Vertical(
        val xs: PaddingValues = PaddingValues(vertical = 4.dp),
        val small: PaddingValues = PaddingValues(vertical = 8.dp),
        val semiRegular: PaddingValues = PaddingValues(vertical = 12.dp),
        val regular: PaddingValues = PaddingValues(vertical = 16.dp),
        val large: PaddingValues = PaddingValues(vertical = 20.dp),
        val xl: PaddingValues = PaddingValues(vertical = 24.dp),
    )

    @Immutable
    data class Horizontal(
        val xs: PaddingValues = PaddingValues(horizontal = 4.dp),
        val small: PaddingValues = PaddingValues(horizontal = 8.dp),
        val semiRegular: PaddingValues = PaddingValues(horizontal = 12.dp),
        val regular: PaddingValues = PaddingValues(horizontal = 16.dp),
        val large: PaddingValues = PaddingValues(horizontal = 20.dp),
        val xl: PaddingValues = PaddingValues(horizontal = 24.dp),
    )

    @Immutable
    data class Space(
        val xxs: Dp = 2.dp,
        val xs: Dp = 4.dp,
        val small: Dp = 8.dp,
        val semiRegular: Dp = 10.dp,
        val regular: Dp = 12.dp,
        val large: Dp = 16.dp,
        val xl: Dp = 20.dp,
        val xxl: Dp = 24.dp,
    )

    @Immutable
    data class Inset(
        val screen: PaddingValues = PaddingValues(16.dp),
        val content: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        val contentSmall: PaddingValues = PaddingValues(8.dp),
        val list: PaddingValues = PaddingValues(horizontal = 16.dp),
    )
}
