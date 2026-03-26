package dot.adun.core.ui.components.textFields.validation

import dot.adun.core.domain.validation.ValidationRule
import dot.adun.core.domain.validation.rules.DigitsValidationRules
import dot.adun.core.domain.validation.rules.EmptinessValidationRules
import dot.adun.core.domain.validation.rules.LengthValidationRules
import dot.adun.core.domain.validation.rules.LettersValidationRules

class DummyTextFieldValidation() : TextFieldValidator() {
    override val rules: List<ValidationRule.Group> = listOf(
        LengthValidationRules(3)
    )
}

class PasswordTextFieldValidation() : TextFieldValidator() {
    override val rules: List<ValidationRule.Group> = listOf(
        EmptinessValidationRules(),
        LengthValidationRules(4, 20),
        LettersValidationRules(),
        DigitsValidationRules()
    )
}
