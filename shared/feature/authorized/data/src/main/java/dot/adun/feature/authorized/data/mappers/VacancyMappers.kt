package dot.adun.feature.authorized.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.core.domain.mappers.toNetwork
import dot.adun.feature.authorized.data.dto.VacancyDto
import dot.adun.feature.authorized.domain.entity.Vacancy

fun String.toVacancyStatus(): Vacancy.Status = when (this) {
    "OPEN" -> Vacancy.Status.Open
    "IN_PROGRESS" -> Vacancy.Status.InProgress
    "CLOSED" -> Vacancy.Status.Closed
    else -> Vacancy.Status.Open
}

fun Vacancy.Status.toNetworkValue(): String = when (this) {
    Vacancy.Status.Open -> "OPEN"
    Vacancy.Status.InProgress -> "IN_PROGRESS"
    Vacancy.Status.Closed -> "CLOSED"
}

fun VacancyDto.toDomainModel(): Vacancy = Vacancy(
    id = id,
    clientId = clientId,
    info = Vacancy.Info(
        title = title,
        description = description
    ),
    requirements = Vacancy.Requirements(
        requiredSkills = requiredSkills,
        experienceYearsRequired = experienceYearsRequired
    ),
    payment = Vacancy.Payment(
        method = paymentMethod.toPaymentType(),
        budget = budget,
        currency = currency.toCurrencyType()
    ),
    status = status.toVacancyStatus(),
    viewsCount = viewsCount,
    createdAt = createdAt.toLocalDateTime(),
    updatedAt = updatedAt.toLocalDateTime(),
    projectId = projectId,
)

fun Vacancy.toNetworkModel(): VacancyDto = VacancyDto(
    id = id,
    clientId = clientId,
    title = info.title,
    description = info.description,
    requiredSkills = requirements.requiredSkills,
    experienceYearsRequired = requirements.experienceYearsRequired,
    paymentMethod = payment.method.toNetworkValue(),
    budget = payment.budget,
    currency = payment.currency.toNetworkValue(),
    durationType = status.toNetworkValue(),
    status = status.toNetworkValue(),
    viewsCount = viewsCount,
    createdAt = createdAt.toNetwork(),
    updatedAt = updatedAt.toNetwork()
)
