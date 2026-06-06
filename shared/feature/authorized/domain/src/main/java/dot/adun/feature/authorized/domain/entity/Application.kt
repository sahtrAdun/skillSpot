package dot.adun.feature.authorized.domain.entity

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Application(
    val id: String,
    val vacancyId: String,
    val resumeId: String,
    val initiator: InitiatorType,
    val status: ApplicationStatus,
    val coverLetter: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

enum class ApplicationStatus {
    Pending,
    Interview,
    Accepted,
    Declined,
    Canceled,
}

enum class InitiatorType {
    Freelancer,
    Client,
}
