package dot.adun.core.ui.entity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import dot.adun.core.domain.validation.Explanation
import dot.adun.core.ui.components.textFields.validation.TextFieldValidation

@Immutable
data class TextFieldData(
    val value: String = "",
    val error: String? = null,
    val helper: Helper? = null,
    val enabled: Boolean = true,
    val jitValidation: Boolean = false,
    val maxChars: Int = Int.MAX_VALUE,
    val validationType: TextFieldValidation? = null,
    val errorFocusRequired: Boolean = false
) {
    @Immutable
    data class Helper(
        val explanation: Explanation
    ) {
        @Composable
        fun text(): String {
            return when (explanation) {
                is Explanation.Resource -> {
                    stringResource(explanation.value, *explanation.args.toTypedArray())
                }
                is Explanation.Text -> explanation.value
            }
        }

        fun toInfo() = copy(
            explanation = explanation.updateHighlight(Explanation.HighlightLevel.Info)
        )
    }

    fun update(value: String, validateEmpty: Boolean = false): TextFieldData {
        val newValue = value.take(maxChars)

        if (validationType == null) return copy(
            value = newValue,
            error = null,
            helper = null
        )

        return if (jitValidation) {
            val result = validationType.isValid(newValue, validateEmpty)
            val newError = newValue.takeIf { !result.isSuccess }

            copy(
                error = newError,
                value = newValue,
                helper = result.helper
            )
        } else {
            copy(
                value = newValue,
                error = null,
                helper = helper?.toInfo()
            )
        }
    }

    fun validate(validateEmpty: Boolean = true): TextFieldData {
        if (validationType == null) return copy(
            error = null,
            helper = null
        )

        val result = validationType.isValid(value, validateEmpty)
        val newError = value.takeIf { !result.isSuccess }

        return copy(
            error = newError,
            helper = result.helper,
            errorFocusRequired = this.hasError && newError != null
        )
    }

    fun enable(): TextFieldData {
        return if (!enabled) copy(enabled = true) else this
    }

    fun disable(): TextFieldData {
        return if (enabled) copy(enabled = false) else this
    }

    fun checkValid(newValue: String): Boolean {
        return validationType?.isValid(newValue)?.isSuccess == true && newValue.length <= maxChars
    }

    val hasError = error != null
}
