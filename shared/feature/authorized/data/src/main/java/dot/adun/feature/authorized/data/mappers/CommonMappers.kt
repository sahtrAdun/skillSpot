package dot.adun.feature.authorized.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.feature.authorized.data.api.response.GetClientActiveProjectsResponse
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType

fun String.toPaymentType(): PaymentType = when (this) {
    "HOURLY" -> PaymentType.Hourly
    "FIXED" -> PaymentType.Fixed
    else -> PaymentType.Fixed
}

fun PaymentType.toNetworkValue(): String = when (this) {
    PaymentType.Hourly -> "HOURLY"
    PaymentType.Fixed -> "FIXED"
}

fun String.toCurrencyType(): Currency = when (this) {
    "USD" -> Currency.USD
    "EUR" -> Currency.EUR
    "RUB" -> Currency.RUB
    "KZT" -> Currency.KZT
    "BYN" -> Currency.BYN
    else -> Currency.USD
}

fun Currency.toNetworkValue(): String = when (this) {
    Currency.USD -> "USD"
    Currency.EUR -> "EUR"
    Currency.RUB -> "RUB"
    Currency.KZT -> "KZT"
    Currency.BYN -> "BYN"
}

fun String.toAvailabilityType(): AvailabilityType = when (this) {
    "FULL_TIME" -> AvailabilityType.FullTime
    "PART_TIME" -> AvailabilityType.PartTime
    "ONE_TIME_TASK" -> AvailabilityType.OneTime
    else -> AvailabilityType.OneTime
}

fun AvailabilityType.toNetworkValue(): String = when (this) {
    AvailabilityType.FullTime -> "FULL_TIME"
    AvailabilityType.PartTime -> "PART_TIME"
    AvailabilityType.OneTime -> "ONE_TIME_TASK"
}

fun GetClientActiveProjectsResponse.toDomainModel(): List<ActiveProject> {
    return when (this) {
        is GetClientActiveProjectsResponse.Ok -> values.map { value ->
            ActiveProject(
                projectId = value.projectId,
                status = value.projectStatus,
                createdAt = value.projectCreatedAt.toLocalDateTime(),
                vacancyId = value.vacancyId,
                vacancyTitle = value.vacancyTitle,
                vacancyBudget = value.vacancyBudget,
                vacancyCurrency = value.vacancyCurrency.toCurrencyType(),
                freelancerId = value.freelancerId,
                freelancerName = value.freelancerName,
                freelancerAvatar = value.freelancerAvatar,
                resumeId = value.resumeId,
                resumeTitle = value.resumeTitle,
            )
        }
        else -> emptyList()
    }
}
