package dot.adun.core.domain

import java.time.Duration
import java.time.LocalTime

data class DayNightState(
    val isDark: Boolean,
    val time: LocalTime
) {
    companion object {
        fun getCurrent(): DayNightState {
            val now = LocalTime.now()
            val isDark = now.isAfter(SUNSET) || now.isBefore(SUNRISE)
            return DayNightState(isDark, now)
        }

        private val SUNRISE = LocalTime.of(6, 0)
        private val SUNSET = LocalTime.of(18, 0)
    }
}

fun calculateNextUpdateTime(): Long {
    val now = LocalTime.now()
    val nextSunrise = LocalTime.of(6, 0)
    val nextSunset = LocalTime.of(18, 0)

    val nextEvent = when {
        now < nextSunrise -> nextSunrise
        now < nextSunset -> nextSunset
        else -> nextSunrise.plusHours(24)
    }

    return Duration.between(now, nextEvent).toMillis()
}
