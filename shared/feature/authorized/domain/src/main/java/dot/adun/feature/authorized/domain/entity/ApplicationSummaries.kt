package dot.adun.feature.authorized.domain.entity

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

/** A freelancer's own application, with a short summary of the target vacancy and partner. */
@Immutable
data class MyApplication(
    val applicationId: String,
    val status: ApplicationStatus,
    val initiator: InitiatorType,
    val coverLetter: String?,
    val createdAt: LocalDateTime,
    val vacancyId: String,
    val vacancyTitle: String,
    val vacancyBudget: Double,
    val vacancyCurrency: Currency,
    val resumeId: String,
    val resumeTitle: String,
    val partnerName: String?,
    val partnerAvatar: String?,
)

/** An application received by a client, with a short summary of the applicant's resume. */
@Immutable
data class IncomingApplication(
    val applicationId: String,
    val status: ApplicationStatus,
    val coverLetter: String?,
    val createdAt: LocalDateTime,
    val vacancyId: String,
    val vacancyTitle: String,
    val resumeId: String,
    val freelancerName: String?,
    val freelancerAvatar: String?,
    val resumeTitle: String,
    val resumeSkills: List<String>,
)
