package dot.adun.feature.chat.ui.mappers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun LocalDateTime.formatTime(): String = format(timeFormatter)
