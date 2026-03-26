package dot.adun.core.domain.validation

import android.content.Context
import javax.annotation.concurrent.Immutable

@Immutable
sealed interface Explanation {
    val highlight: HighlightLevel

    fun contentKey(): String
    fun updateHighlight(new: HighlightLevel): Explanation

    @Immutable
    data class Resource(
        val value: Int,
        val args: List<Any> = emptyList(),
        override val highlight: HighlightLevel = HighlightLevel.Error
    ) : Explanation {
        override fun contentKey(): String = value.toString()
        override fun updateHighlight(new: HighlightLevel): Explanation = Resource(
            value = value,
            args = args,
            highlight = new
        )

        fun getString(context: Context): String {
            return context.getString(value, args)
        }
    }

    @Immutable
    data class Text(
        val value: String,
        override val highlight: HighlightLevel = HighlightLevel.Error
    ) : Explanation {
        override fun contentKey(): String = value
        override fun updateHighlight(new: HighlightLevel): Explanation = Text(
            value = value,
            highlight = new
        )
    }

    enum class HighlightLevel(val level: Int) {
        Error(0), Warning(1), Highlight(2), Info(3)
    }
}
