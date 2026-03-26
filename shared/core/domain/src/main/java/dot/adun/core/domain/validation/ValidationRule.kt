package dot.adun.core.domain.validation

interface ValidationRule {
    interface Group {
        fun validate(value: String): Boolean
        val explanation: Explanation?
        val withMinors: Boolean
    }

    interface Type {
        val res: Int
        val args: List<Any>
        val highlight: Explanation.HighlightLevel
        val condition: () -> Boolean

        val isError: Boolean get() = highlight == Explanation.HighlightLevel.Error
    }
}

abstract class RulesValidator() : ValidationRule.Group {
    abstract fun buildRules(value: String): List<ValidationRule.Type>
    override val withMinors: Boolean = true
    override var explanation: Explanation? = null
        protected set

    override fun validate(value: String): Boolean {
        val triggeredRule = buildRules(value)
            .sortedBy { it.highlight.level }
            .find { it.condition() }

        return if (triggeredRule != null) {
            explanation = Explanation.Resource(
                value = triggeredRule.res,
                args = triggeredRule.args,
                highlight = triggeredRule.highlight
            )

            !triggeredRule.isError
        } else {
            explanation = null
            true
        }
    }
}
