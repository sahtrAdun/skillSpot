package dot.adun.core.domain.validation

import javax.annotation.concurrent.Immutable

@Immutable
data class Critical(
    override val res: Int,
    override val args: List<Any> = emptyList(),
    override val condition: () -> Boolean
) : ValidationRule.Type {
    override val highlight: Explanation.HighlightLevel = Explanation.HighlightLevel.Error
}

@Immutable
data class Minor(
    override val res: Int,
    override val args: List<Any> = emptyList(),
    override val condition: () -> Boolean
) : ValidationRule.Type {
    override val highlight: Explanation.HighlightLevel = Explanation.HighlightLevel.Warning
}

@Immutable
data class Highlight(
    override val res: Int,
    override val args: List<Any> = emptyList(),
    override val condition: () -> Boolean
) : ValidationRule.Type {
    override val highlight: Explanation.HighlightLevel = Explanation.HighlightLevel.Highlight
}
