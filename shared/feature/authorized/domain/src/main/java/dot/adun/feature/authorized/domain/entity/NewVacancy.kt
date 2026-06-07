package dot.adun.feature.authorized.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
data class NewVacancy(
    val title: String,
    val description: String,
    val requiredSkills: List<String>,
    val experienceYears: Int?,
    val paymentMethod: PaymentType,
    val budget: Double,
    val currency: Currency,
    val durationType: AvailabilityType,
)
