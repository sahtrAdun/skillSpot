package dot.adun.core.domain.validation.rules

import dot.adun.common.resources.CommonStrings
import dot.adun.core.domain.validation.Critical
import dot.adun.core.domain.validation.Minor
import dot.adun.core.domain.validation.RulesValidator
import dot.adun.core.domain.validation.ValidationRule

class DigitsValidationRules(
    private val recommendedDigitsCount: Int = 3,
    withWarnings: Boolean = true,
) : RulesValidator() {
    override val withMinors: Boolean = withWarnings

    override fun buildRules(value: String): List<ValidationRule.Type> = listOf(
        Critical(CommonStrings.validation_no_digits) { value.any { it.isDigit() }.not() },
        Minor(CommonStrings.validation_digits_count, listOf(recommendedDigitsCount)) { value.count { it.isDigit() } < recommendedDigitsCount }
    )
}
