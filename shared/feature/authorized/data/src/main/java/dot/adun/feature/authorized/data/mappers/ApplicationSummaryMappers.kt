package dot.adun.feature.authorized.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.feature.authorized.data.dto.IncomingApplicationDto
import dot.adun.feature.authorized.data.dto.MyApplicationDto
import dot.adun.feature.authorized.domain.entity.IncomingApplication
import dot.adun.feature.authorized.domain.entity.MyApplication

fun MyApplicationDto.toDomainModel(): MyApplication = MyApplication(
    applicationId = applicationId,
    status = applicationStatus.toApplicationStatus(),
    initiator = initiator.toInitiatorType(),
    coverLetter = coverLetter,
    createdAt = createdAt.toLocalDateTime(),
    vacancyId = vacancyId,
    vacancyTitle = vacancyTitle,
    vacancyBudget = vacancyBudget,
    vacancyCurrency = vacancyCurrency.toCurrencyType(),
    resumeId = resumeId,
    resumeTitle = resumeTitle,
    partnerName = partnerFullName,
    partnerAvatar = partnerAvatarUrl,
)

fun IncomingApplicationDto.toDomainModel(): IncomingApplication = IncomingApplication(
    applicationId = applicationId,
    status = applicationStatus.toApplicationStatus(),
    coverLetter = coverLetter,
    createdAt = createdAt.toLocalDateTime(),
    vacancyId = vacancyId,
    vacancyTitle = vacancyTitle,
    resumeId = resumeId,
    freelancerName = freelancerName,
    freelancerAvatar = freelancerAvatar,
    resumeTitle = resumeTitle,
    resumeSkills = resumeSkills,
)
