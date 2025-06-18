package arcanegolem.yms.data.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

private val YYYYMMDD_LD = LocalDate.Format {
  year()
  char('-')
  monthNumber()
  char('-')
  dayOfMonth()
}

internal fun Long.toDateString() : String {
  val instant = Instant.fromEpochMilliseconds(this)
  val localDate = instant.toLocalDateTime(TimeZone.UTC).date

  return localDate.format(YYYYMMDD_LD)
}

internal fun monthStartMillis() : Long {
  val today = Clock.System.now().toLocalDateTime(TimeZone.UTC)
  val monthStart = LocalDateTime(today.year, today.month, 1, 0, 0, 0)

  return monthStart.toInstant(TimeZone.UTC).toEpochMilliseconds()
}

internal fun todayMillis() : Long {
  return Clock.System.now().toEpochMilliseconds()
}

internal fun String.parseMillis() : Long {
  return Instant.parse(this).toEpochMilliseconds()
}




