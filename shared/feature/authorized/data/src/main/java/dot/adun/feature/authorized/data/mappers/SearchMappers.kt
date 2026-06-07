package dot.adun.feature.authorized.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.feature.authorized.data.dto.SearchResumeDto
import dot.adun.feature.authorized.data.dto.SearchVacancyDto
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy

fun SearchVacancyDto.toDomainModel(): Vacancy {
    val createdAt = createdAt.toLocalDateTime()
    return Vacancy(
        id = id,
        clientId = clientId,
        info = Vacancy.Info(
            title = title,
            description = description,
        ),
        requirements = Vacancy.Requirements(
            requiredSkills = requiredSkills,
            experienceYearsRequired = experienceYearsRequired,
        ),
        payment = Vacancy.Payment(
            method = paymentMethod.toPaymentType(),
            budget = budget,
            currency = currency.toCurrencyType(),
        ),
        status = status.toVacancyStatus(),
        viewsCount = viewsCount,
        createdAt = createdAt,
        updatedAt = updatedAt?.toLocalDateTime() ?: createdAt,
    )
}

fun SearchResumeDto.toDomainModel(): Resume {
    val createdAt = createdAt.toLocalDateTime()
    return Resume(
        id = id,
        freelancerId = freelancerId,
        title = title,
        bio = bio,
        links = Resume.Links(
            githubUrl = githubUrl,
            portfolioUrl = portfolioUrl,
        ),
        skills = Resume.Skills(
            main = mainSkills,
            secondary = secondarySkills,
        ),
        paymentInfo = Resume.PaymentInfo(
            preference = paymentPreference.toPaymentType(),
            minRate = minRate,
            currency = currency.toCurrencyType(),
        ),
        availability = availability.map { it.toAvailabilityType() },
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = createdAt,
    )
}
