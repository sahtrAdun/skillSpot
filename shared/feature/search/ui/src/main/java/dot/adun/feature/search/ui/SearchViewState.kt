package dot.adun.feature.search.ui

import androidx.compose.runtime.Immutable
import dot.adun.core.ui.components.textFields.validation.PasswordTextFieldValidation
import dot.adun.core.ui.entity.TextFieldData

@Immutable
data class SearchViewState(
    val textField: TextFieldData = TextFieldData(
        validationType = PasswordTextFieldValidation(),
        jitValidation = true
    )
)
