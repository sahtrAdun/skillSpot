package dot.adun.feature.authorized.ui.screen.details.vacancy.edit

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class VacancyEditViewState(
    val vacancy: Vacancy? = null,
    val titleField: TextFieldData = TextFieldData(),
    val descriptionField: TextFieldData = TextFieldData(),
    val skills: List<String> = emptyList(),
    val skillInput: String = "",
    val experienceYears: String = "",
    val paymentMethod: PaymentType = PaymentType.Hourly,
    val budget: String = "",
    val currency: Currency = Currency.USD,
    val loadState: LoadState = LoadState.NotStarted,
    val saveState: LoadState = LoadState.NotStarted,
) {
    val isValid: Boolean
        get() = titleField.value.isNotBlank() && budget.toDoubleOrNull() != null

    fun toBudgetDouble(): Double? = budget.toDoubleOrNull()
}

sealed interface VacancyEditScreenResult {
    data object Finish : VacancyEditScreenResult
    data object Saved : VacancyEditScreenResult
}
