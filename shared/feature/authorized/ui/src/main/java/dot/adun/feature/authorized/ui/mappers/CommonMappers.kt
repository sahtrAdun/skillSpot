package dot.adun.feature.authorized.ui.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.domain.entity.resRef
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.R

@Composable
fun buildPaymentLabel(payment: Resume.PaymentInfo): String {
    val amount = stringResource(
        R.string.payment_from,
        buildAmount(payment.currency, payment.minRate)
    )
    return when (payment.preference) {
        PaymentType.Hourly -> stringResource(R.string.per_hour, amount)
        PaymentType.Fixed -> amount
    }
}

@Composable
fun buildPaymentLabel(payment: Vacancy.Payment): String {
    val amount = buildAmount(payment.currency, payment.budget)
    return when (payment.method) {
        PaymentType.Hourly -> stringResource(R.string.per_hour, amount)
        PaymentType.Fixed -> amount
    }
}

fun buildAmount(currency: Currency, amount: Double): String =
    "${currency.symbol}${formatRate(amount)}"
fun formatRate(rate: Double): String {
    return if (rate == rate.toLong().toDouble()) {
        rate.toLong().toString()
    } else {
        String.format("%.2f", rate) // todo locale separator
    }
}

val Currency.symbol: String
    get() = when (this) {
        Currency.USD -> "$"
        Currency.EUR -> "\u20AC"
        Currency.RUB -> "\u20BD"
        Currency.KZT -> "\u20B8"
        Currency.BYN -> "Br"
    }

val AvailabilityType.label: TextRef
    get() = when (this) {
        AvailabilityType.FullTime -> resRef(R.string.full_time)
        AvailabilityType.PartTime -> resRef(R.string.part_time)
        AvailabilityType.OneTime -> resRef(R.string.one_time)
    }

val Vacancy.Status.label: TextRef
    get() = when (this) {
        Vacancy.Status.Open -> resRef(Res.strings.vacancy_status_open)
        Vacancy.Status.InProgress -> resRef(Res.strings.vacancy_status_progress)
        Vacancy.Status.Closed -> resRef(Res.strings.vacancy_status_closed)
    }
