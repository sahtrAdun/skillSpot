package dot.adun.core.domain.validation.rules

import dot.adun.common.resources.Res
import dot.adun.core.domain.validation.Critical
import dot.adun.core.domain.validation.RulesValidator
import dot.adun.core.domain.validation.ValidationRule

class LengthValidationRules(
    minLength: Int = 0,
    maxLength: Int = Int.MAX_VALUE
) : RulesValidator() {
    private val minLen: Int = minLength
    private val maxLen: Int = maxLength

    override fun buildRules(value: String): List<ValidationRule.Type> = listOf(
        Critical(Res.strings.validation_text_len_min, listOf(minLen)) { value.length < minLen },
        Critical(Res.strings.validation_text_len_max, listOf(maxLen)) { value.length > maxLen }
    )
}
