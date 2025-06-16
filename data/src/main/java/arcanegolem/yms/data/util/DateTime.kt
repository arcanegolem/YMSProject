package arcanegolem.yms.data.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val YYYYMMDD = LocalDateTime.Format {
  year()
  char('-')
  monthNumber()
  char('-')
  dayOfMonth()
}

fun Long.toDateString() : String {
  val instant = Instant.fromEpochMilliseconds(this)
  val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

  return localDateTime.format(YYYYMMDD)
}