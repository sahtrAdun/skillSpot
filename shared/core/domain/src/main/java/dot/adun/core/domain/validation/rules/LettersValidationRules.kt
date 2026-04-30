package dot.adun.core.domain.validation.rules

import dot.adun.common.resources.Res
import dot.adun.core.domain.validation.Critical
import dot.adun.core.domain.validation.Minor
import dot.adun.core.domain.validation.RulesValidator
import dot.adun.core.domain.validation.ValidationRule

class LettersValidationRules(
    private val validateUppers: Boolean = true,
    private val recommendedLettersCount: Int = 3,
    withWarnings: Boolean = true,
) : RulesValidator() {
    override val withMinors: Boolean = withWarnings

    override fun buildRules(value: String): List<ValidationRule.Type> = listOf(
        Critical(Res.strings.validation_no_letters) { !value.any { it.isLetter() } },
        Critical(Res.strings.validation_no_upper_letters) { !value.any { it.isUpperCase() } && validateUppers },
        Minor(
            res = Res.strings.validation_letters_count,
            args = listOf(recommendedLettersCount)
        ) { value.count { it.isLetter() } < recommendedLettersCount }
    )
}
