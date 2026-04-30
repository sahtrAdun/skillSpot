package dot.adun.core.domain.validation.rules

import dot.adun.common.resources.Res
import dot.adun.core.domain.validation.Critical
import dot.adun.core.domain.validation.RulesValidator
import dot.adun.core.domain.validation.ValidationRule

class EmptinessValidationRules() : RulesValidator() {
    override fun buildRules(value: String): List<ValidationRule.Type> = listOf(
        Critical(Res.strings.validation_empty_value) { value.isEmpty() }
    )
}
