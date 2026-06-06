package dot.adun.feature.authorized.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.feature.authorized.data.dto.ApplicationDto
import dot.adun.feature.authorized.domain.entity.Application
import dot.adun.feature.authorized.domain.entity.ApplicationStatus
import dot.adun.feature.authorized.domain.entity.InitiatorType

fun ApplicationDto.toDomainModel(): Application = Application(
    id = id,
    vacancyId = vacancyId,
    resumeId = resumeId,
    initiator = initiator.toInitiatorType(),
    status = status.toApplicationStatus(),
    coverLetter = coverLetter,
    createdAt = createdAt.toLocalDateTime(),
    updatedAt = updatedAt.toLocalDateTime(),
)

fun String.toInitiatorType(): InitiatorType = when (uppercase()) {
    "FREELANCER" -> InitiatorType.Freelancer
    "CLIENT" -> InitiatorType.Client
    else -> InitiatorType.Freelancer
}

fun String.toApplicationStatus(): ApplicationStatus = when (uppercase()) {
    "PENDING" -> ApplicationStatus.Pending
    "INTERVIEW" -> ApplicationStatus.Interview
    "ACCEPTED" -> ApplicationStatus.Accepted
    "DECLINED" -> ApplicationStatus.Declined
    "CANCELED" -> ApplicationStatus.Canceled
    else -> ApplicationStatus.Pending
}
