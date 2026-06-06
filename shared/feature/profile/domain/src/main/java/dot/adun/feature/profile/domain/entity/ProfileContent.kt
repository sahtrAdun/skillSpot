package dot.adun.feature.profile.domain.entity

import javax.annotation.concurrent.Immutable

/**
 * The content a profile owns, depending on its role. A freelancer exposes resumes,
 * a customer exposes vacancies.
 */
@Immutable
sealed interface ProfileContent {

    @Immutable
    data class Vacancies(val items: List<ProfileVacancy>) : ProfileContent

    @Immutable
    data class Resumes(val items: List<ProfileResume>) : ProfileContent

    @Immutable
    data object Empty : ProfileContent
}

@Immutable
data class ProfileVacancy(
    val id: String,
    val title: String,
    val skills: List<String>,
    val budget: Double,
    val currency: String,
    val paymentMethod: String,
    val status: String,
)

@Immutable
data class ProfileResume(
    val id: String,
    val title: String,
    val skills: List<String>,
    val minRate: Double,
    val currency: String,
    val paymentPreference: String,
    val isActive: Boolean,
)
