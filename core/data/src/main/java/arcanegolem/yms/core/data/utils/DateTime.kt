package arcanegolem.yms.core.data.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private val YYYYMMDD_LD = LocalDate.Format {
  year()
  char('-')
  monthNumber()
  char('-')
  day(padding = Padding.ZERO)
}

@OptIn(ExperimentalTime::class)
fun Long.toDateString() : String {
  val instant = Instant.fromEpochMilliseconds(this)
  val localDate = instant.toLocalDateTime(TimeZone.UTC).date

  return localDate.format(YYYYMMDD_LD)
}

@OptIn(ExperimentalTime::class)
fun monthStartMillis() : Long {
  val today = Clock.System.now().toLocalDateTime(TimeZone.UTC)
  val monthStart = LocalDateTime(today.year, today.month, 1, 0, 0, 0)

  return monthStart.toInstant(TimeZone.UTC).toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun todayMillis() : Long {
  return Clock.System.now().toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun String.parseMillis() : Long {
  return Instant.parse(this).toEpochMilliseconds()
}




