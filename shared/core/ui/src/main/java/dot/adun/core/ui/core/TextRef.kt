package dot.adun.core.ui.core

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import javax.annotation.concurrent.Immutable

@Immutable
sealed interface TextRef {
    @Immutable
    data class String(val value: kotlin.String) : TextRef

    @Immutable
    data class Res(@param:StringRes val res: Int) : TextRef
}

fun stringRef(value: String): TextRef.String {
    return TextRef.String(value)
}

fun resRef(@StringRes res: Int): TextRef.Res {
    return TextRef.Res(res)
}

@Composable
fun TextRef.display(vararg args: Any?): String {
    return when (val ref = this) {
        is TextRef.String -> ref.value
        is TextRef.Res -> stringResource(ref.res, args)
    }
}

@Composable
fun TextRef.display(): String {
    return when (val ref = this) {
        is TextRef.String -> ref.value
        is TextRef.Res -> stringResource(ref.res)
    }
}
