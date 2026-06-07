package dot.adun.feature.authorized.ui.screen.create.resume

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType

@Immutable
data class CreateResumeViewState(
    val titleField: TextFieldData = TextFieldData(),
    val bioField: TextFieldData = TextFieldData(),
    val mainSkills: List<String> = emptyList(),
    val mainSkillInput: String = "",
    val secondarySkills: List<String> = emptyList(),
    val secondarySkillInput: String = "",
    val paymentPreference: PaymentType = PaymentType.Hourly,
    val minRate: String = "",
    val currency: Currency = Currency.USD,
    val availability: List<AvailabilityType> = emptyList(),
    val githubUrl: String = "",
    val portfolioUrl: String = "",
    val saveState: LoadState = LoadState.NotStarted,
) {
    val isValid: Boolean
        get() = titleField.value.isNotBlank() && minRate.toDoubleOrNull() != null
}

sealed interface CreateResumeScreenResult {
    data object Finish : CreateResumeScreenResult
    data object Created : CreateResumeScreenResult
}
