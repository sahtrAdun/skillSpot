package dot.adun.feature.authorized.ui.screen.create.vacancy

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType

@Immutable
data class CreateVacancyViewState(
    val titleField: TextFieldData = TextFieldData(),
    val descriptionField: TextFieldData = TextFieldData(),
    val skills: List<String> = emptyList(),
    val skillInput: String = "",
    val experienceYears: String = "",
    val paymentMethod: PaymentType = PaymentType.Hourly,
    val budget: String = "",
    val currency: Currency = Currency.USD,
    val durationType: AvailabilityType = AvailabilityType.FullTime,
    val saveState: LoadState = LoadState.NotStarted,
) {
    val isValid: Boolean
        get() = titleField.value.isNotBlank() && budget.toDoubleOrNull() != null
}

sealed interface CreateVacancyScreenResult {
    data object Finish : CreateVacancyScreenResult
    data object Created : CreateVacancyScreenResult
}
