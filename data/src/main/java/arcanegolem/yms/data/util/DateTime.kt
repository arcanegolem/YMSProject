package arcanegolem.yms.data.util

import androidx.compose.ui.text.intl.LocaleList
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val YYYYMMDD_LD = LocalDate.Format {
  year()
  char('-')
  monthNumber()
  char('-')
  dayOfMonth()
}

private val ReadableFormat = LocalDateTime.Format {
  dayOfMonth()
  char(' ')
  monthName(getMonthNames())
  char(' ')
  year()
}

private val ReadableFormatWithTime = LocalDateTime.Format {
  dayOfMonth()
  char(' ')
  monthName(getMonthNames())
  char(' ')
  year()
  char(' ')
  hour()
  char(':')
  minute()
}

internal fun getMonthNames() : MonthNames {
  val lang = LocaleList.current.localeList[0].language

  return when (lang) {
    "en" -> MonthNames.ENGLISH_FULL
    "ru" -> MonthNames(listOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"))
    else -> MonthNames.ENGLISH_ABBREVIATED
  }
}

internal fun Long.toDateString() : String {
  val instant = Instant.fromEpochMilliseconds(this)
  val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

  return localDate.format(YYYYMMDD_LD)
}

fun monthStartFormatted() : String {
  val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
  val monthStart = LocalDateTime(today.year, today.month, 1, 0, 0, 0)

  return monthStart.format(ReadableFormat)
}

fun todayWithTimeFormatted() : String {
  return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).format(ReadableFormatWithTime)
}