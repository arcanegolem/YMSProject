package arcanegolem.yms.core.data.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
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

private val DDMMYYYY_DOTTED = LocalDate.Format {
  day(padding = Padding.ZERO)
  char('.')
  monthNumber()
  char('.')
  year()
}

private val HHMM_DOUBLE_DOTTED = LocalTime.Format {
  hour()
  char(':')
  minute()
}

private val DDMMYY_HHMMSS = LocalDateTime.Format {
  hour()
  char(':')
  minute()
  char(':')
  second()
  char(' ')
  day()
  char('.')
  monthNumber()
  char('.')
  year()
}

@OptIn(ExperimentalTime::class)
fun Long.toDateStringYYYYMMDD() : String {
  val instant = Instant.fromEpochMilliseconds(this)
  val localDate = instant.toLocalDateTime(TimeZone.UTC).date

  return localDate.format(YYYYMMDD_LD)
}

@OptIn(ExperimentalTime::class)
fun Long.getDayNumberFromMillis() : Int {
  val instant = Instant.fromEpochMilliseconds(this)
  return instant.toLocalDateTime(TimeZone.UTC).date.day
}

@OptIn(ExperimentalTime::class)
fun currentDateTimeAsString() : String {
  val instant = Clock.System.now()
  val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

  return localDateTime.format(DDMMYY_HHMMSS)
}

@OptIn(ExperimentalTime::class)
fun currentDateTimeAsInstantString() : String {
  val instant = Clock.System.now()
  val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

  return localDateTime.toInstant(TimeZone.UTC).toString()
}

@OptIn(ExperimentalTime::class)
fun dateMillisStartDay() : Long {
  val instant = Clock.System.now()
  val localDate = instant.toLocalDateTime(TimeZone.UTC).date

  return localDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun dateMillisEndDay() : Long {
  val instant = Clock.System.now()
  val localDate = instant.toLocalDateTime(TimeZone.UTC).date

  return localDate.atTime(23, 59, 59).toInstant(TimeZone.UTC).toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun Long.toDateStringDDMMYYYY() : String {
  val instant = Instant.fromEpochMilliseconds(this)
  val localDate = instant.toLocalDateTime(TimeZone.UTC).date

  return localDate.format(DDMMYYYY_DOTTED)
}

@OptIn(ExperimentalTime::class)
fun monthStartMillis() : Long {
  val today = Clock.System.now().toLocalDateTime(TimeZone.UTC)
  val monthStart = LocalDateTime(today.year, today.month, 1, 0, 0, 0)

  return monthStart.toInstant(TimeZone.UTC).toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun todayMillis() : Long {
  return dateMillisEndDay()
}

@OptIn(ExperimentalTime::class)
fun String.parseMillis() : Long {
  return Instant.parse(this).toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun compositeDate(date : String, time : String) : String {
  val localDate = LocalDate.parse(date, DDMMYYYY_DOTTED)
  val localTime = LocalTime.parse(time, HHMM_DOUBLE_DOTTED)
  val localDateTime = LocalDateTime(localDate, localTime)
  return localDateTime.toInstant(TimeZone.UTC).toString()
}

@OptIn(ExperimentalTime::class)
fun String.getDateFromInstantString() : String {
  return Instant.parse(this).toLocalDateTime(TimeZone.UTC).date.format(DDMMYYYY_DOTTED)
}

@OptIn(ExperimentalTime::class)
fun String.getTimeFromInstantString() : String {
  return Instant.parse(this).toLocalDateTime(TimeZone.UTC).time.format(HHMM_DOUBLE_DOTTED)
}




