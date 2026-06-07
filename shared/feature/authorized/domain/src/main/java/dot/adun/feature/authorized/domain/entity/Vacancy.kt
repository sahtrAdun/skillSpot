package dot.adun.feature.authorized.domain.entity

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Vacancy(
    val id: String,
    val clientId: String,
    val info: Info,
    val requirements: Requirements,
    val payment: Payment,
    val status: Status,
    val viewsCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    /** Present only for active working vacancies — the related project's id. */
    val projectId: String? = null,
) {
    @Immutable
    data class Info(
        val title: String,
        val description: String
    )

    @Immutable
    data class Requirements(
        val requiredSkills: List<String>,
        val experienceYearsRequired: Int?
    )

    @Immutable
    data class Payment(
        val method: PaymentType,
        val budget: Double,
        val currency: Currency
    )

    enum class Status {
        Open,
        InProgress,
        Closed
    }
}
