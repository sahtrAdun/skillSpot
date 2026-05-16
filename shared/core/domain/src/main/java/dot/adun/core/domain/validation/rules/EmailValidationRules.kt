package dot.adun.core.domain.validation.rules

import dot.adun.common.resources.Res
import dot.adun.core.domain.validation.Critical
import dot.adun.core.domain.validation.Minor
import dot.adun.core.domain.validation.RulesValidator
import dot.adun.core.domain.validation.ValidationRule

class EmailValidationRules(
    withWarnings: Boolean = true
) : RulesValidator() {
    override val withMinors: Boolean = withWarnings

    private val emailRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                ")+"
    )

    override fun buildRules(value: String): List<ValidationRule.Type> = listOf(
        Critical(Res.strings.validation_email_empty) { value.isBlank() },
        Critical(Res.strings.validation_email_invalid) {
            value.isNotBlank() && !emailRegex.matches(
                value
            )
        },
        Minor(Res.strings.validation_email_too_long) { value.length > 100 }
    )
}
