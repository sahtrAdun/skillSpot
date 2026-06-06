package dot.adun.feature.profile.ui.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.profile.ui.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun LocalDateTime.formatDate(): String = format(dateFormatter)

@Composable
fun UserRole.label(): String? = when (this) {
    UserRole.Freelancer -> stringResource(R.string.profile_role_freelancer)
    UserRole.Customer -> stringResource(R.string.profile_role_customer)
    UserRole.None -> null
}

fun currencySymbol(code: String): String = when (code.uppercase()) {
    "USD" -> "$"
    "EUR" -> "€"
    "RUB" -> "₽"
    "KZT" -> "₸"
    "BYN" -> "Br"
    else -> code
}

fun formatAmount(currency: String, amount: Double): String {
    val number = if (amount == amount.toLong().toDouble()) {
        amount.toLong().toString()
    } else {
        String.format("%.2f", amount)
    }
    return "${currencySymbol(currency)}$number"
}

fun formatRating(rating: Float): String =
    if (rating == rating.toLong().toFloat()) {
        rating.toLong().toString()
    } else {
        String.format("%.1f", rating)
    }

fun isHourly(paymentMethod: String): Boolean = paymentMethod.equals("hourly", ignoreCase = true)
