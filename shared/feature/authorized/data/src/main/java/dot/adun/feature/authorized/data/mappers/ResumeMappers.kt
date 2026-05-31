package dot.adun.feature.authorized.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.core.domain.mappers.toNetwork
import dot.adun.feature.authorized.data.dto.ResumeDto
import dot.adun.feature.authorized.domain.entity.Resume

fun ResumeDto.toDomainModel(): Resume = Resume(
    id = id,
    freelancerId = freelancerId,
    title = title,
    bio = bio,
    links = Resume.Links(
        githubUrl = githubUrl,
        portfolioUrl = portfolioUrl
    ),
    skills = Resume.Skills(
        main = mainSkills,
        secondary = secondarySkills
    ),
    paymentInfo = Resume.PaymentInfo(
        preference = paymentPreference.toPaymentType(),
        minRate = minRate,
        currency = currency.toCurrencyType()
    ),
    availability = availability.map { it.toAvailabilityType() },
    isActive = isActive,
    createdAt = createdAt.toLocalDateTime(),
    updatedAt = updatedAt.toLocalDateTime()
)

fun Resume.toNetworkModel(): ResumeDto = ResumeDto(
    id = id,
    freelancerId = freelancerId,
    title = title,
    bio = bio,
    githubUrl = links.githubUrl,
    portfolioUrl = links.portfolioUrl,
    mainSkills = skills.main,
    secondarySkills = skills.secondary,
    paymentPreference = paymentInfo.preference.toNetworkValue(),
    minRate = paymentInfo.minRate,
    currency = paymentInfo.currency.toNetworkValue(),
    availability = availability.map { it.toNetworkValue() },
    isActive = isActive,
    createdAt = createdAt.toNetwork(),
    updatedAt = updatedAt.toNetwork()
)
