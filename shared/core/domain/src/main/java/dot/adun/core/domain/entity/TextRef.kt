package dot.adun.core.domain.entity

import androidx.annotation.StringRes
import javax.annotation.concurrent.Immutable

interface TextRef {
    @Immutable
    data class StringRef(
        val value: String
    ) : TextRef

    @Immutable
    data class ResourceRef(
        @param:StringRes val value: Int
    ) : TextRef
}

fun strRef(value: String): TextRef.StringRef {
    return TextRef.StringRef(value)
}

fun resRef(@StringRes resource: Int): TextRef.ResourceRef {
    return TextRef.ResourceRef(resource)
}
