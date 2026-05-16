package dot.adun.feature.register.ui.screen

import androidx.compose.runtime.Immutable
import dot.adun.core.ui.components.textFields.validation.EmailTextFieldValidation
import dot.adun.core.ui.components.textFields.validation.PasswordTextFieldValidation
import dot.adun.core.ui.entity.TextFieldData

@Immutable
data class RegisterViewState(
    val emailField: TextFieldData = TextFieldData(
        validationType = EmailTextFieldValidation()
    ),
    val passwordField: TextFieldData = TextFieldData(
        validationType = PasswordTextFieldValidation(),
        jitValidation = true
    )
) {
    val isValid: Boolean = !emailField.hasError && !passwordField.hasError
}
