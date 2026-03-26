package dot.adun.core.ui.components.textFields.validation

import dot.adun.core.domain.validation.Explanation
import dot.adun.core.domain.validation.ValidationRule
import dot.adun.core.ui.mappers.helper

interface TextFieldValidation {
    val rules: List<ValidationRule.Group>
    fun isValid(value: String, validateEmpty: Boolean = false): ValidationResult
}

abstract class TextFieldValidator() : TextFieldValidation {
    protected fun checkRulesFor(value: String): ValidationResult {
        val results = rules.mapNotNull { rule ->
            rule.validate(value)
            val explanation = rule.explanation

            when (explanation?.highlight) {
                Explanation.HighlightLevel.Error -> ValidationResult.Error(explanation.helper())
                Explanation.HighlightLevel.Warning -> ValidationResult.Warning(explanation.helper())
                Explanation.HighlightLevel.Highlight -> ValidationResult.Highlight(explanation.helper())
                else -> null
            }
        }

        return results.firstOrNull { it is ValidationResult.Error }
            ?: results.firstOrNull { it is ValidationResult.Warning }
            ?: results.firstOrNull { it is ValidationResult.Highlight }
            ?: ValidationResult.Success
    }

    override fun isValid(value: String, validateEmpty: Boolean): ValidationResult {
        if (value.isEmpty() && !validateEmpty) return ValidationResult.Success

        return checkRulesFor(value)
    }
}
