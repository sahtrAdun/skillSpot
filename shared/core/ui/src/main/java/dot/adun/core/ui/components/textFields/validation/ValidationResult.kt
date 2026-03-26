package dot.adun.core.ui.components.textFields.validation

import dot.adun.core.ui.entity.TextFieldData

sealed interface ValidationResult {
    object Success : ValidationResult

    data class Error(val fieldHelper: TextFieldData.Helper) : ValidationResult
    data class Warning(val fieldHelper: TextFieldData.Helper) : ValidationResult
    data class Highlight(val fieldHelper: TextFieldData.Helper) : ValidationResult

    val isSuccess: Boolean get() = this !is Error
    val helper: TextFieldData.Helper? get() = when (this) {
        is Error -> this.fieldHelper
        is Warning -> this.fieldHelper
        is Highlight -> this.fieldHelper
        Success -> null
    }
}
