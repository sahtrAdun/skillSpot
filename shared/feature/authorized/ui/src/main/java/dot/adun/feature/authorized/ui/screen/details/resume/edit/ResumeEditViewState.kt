package dot.adun.feature.authorized.ui.screen.details.resume.edit

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.domain.entity.Resume

@Immutable
data class ResumeEditViewState(
    val resume: Resume? = null,
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
    val isActive: Boolean = true,
    val loadState: LoadState = LoadState.NotStarted,
    val saveState: LoadState = LoadState.NotStarted,
) {
    val isValid: Boolean
        get() = titleField.value.isNotBlank() && minRate.toDoubleOrNull() != null
}

sealed interface ResumeEditScreenResult {
    data object Finish : ResumeEditScreenResult
    data object Saved : ResumeEditScreenResult
}
