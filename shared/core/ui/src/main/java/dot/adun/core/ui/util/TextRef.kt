package dot.adun.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dot.adun.core.domain.entity.TextRef

@Composable
fun TextRef.display(vararg args: Any?): String {
    return when (val ref = this) {
        is TextRef.StringRef -> ref.value
        is TextRef.ResourceRef -> stringResource(ref.value, args)
        else -> ""
    }
}

@Composable
fun TextRef.display(): String {
    return when (val ref = this) {
        is TextRef.StringRef -> ref.value
        is TextRef.ResourceRef -> stringResource(ref.value)
        else -> ""
    }
}