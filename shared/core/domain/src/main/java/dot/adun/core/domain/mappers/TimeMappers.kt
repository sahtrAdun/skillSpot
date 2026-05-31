package dot.adun.core.domain.mappers

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

fun String.toLocalDateTime(): LocalDateTime {
    return OffsetDateTime.parse(this, networkFormatter)
        .atZoneSameInstant(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun LocalDateTime.toNetwork(): String {
    return this.atZone(ZoneId.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)
        .toOffsetDateTime()
        .format(networkFormatter)
}

private val networkFormatter = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
    .appendPattern("XXX")
    .toFormatter()
