package dot.adun.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class AdunShapes(
    val extraSmall: RoundedCornerShape = RoundedCornerShape(4.dp),
    val small: RoundedCornerShape = RoundedCornerShape(8.dp),
    val semiMedium: RoundedCornerShape = RoundedCornerShape(10.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val xl: RoundedCornerShape = RoundedCornerShape(20.dp),
    val xxl: RoundedCornerShape = RoundedCornerShape(24.dp)
)
