package dot.adun.feature.authorized.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
data class NewResume(
    val title: String,
    val bio: String?,
    val githubUrl: String?,
    val portfolioUrl: String?,
    val mainSkills: List<String>,
    val secondarySkills: List<String>?,
    val paymentPreference: PaymentType,
    val minRate: Double,
    val currency: Currency,
    val availability: List<AvailabilityType>,
)
