package arcanegolem.yms.project.util

import androidx.compose.ui.text.intl.LocaleList
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun getMonthNames() : MonthNames {
  val lang = LocaleList.current.localeList[0].language

  return when (lang) {
    "en" -> MonthNames.ENGLISH_FULL
    "ru" -> MonthNames(listOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"))
    else -> MonthNames.ENGLISH_ABBREVIATED
  }
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
  char('.')
  monthNumber()
  char('.')
  year()
  char(' ')
  hour()
  char(':')
  minute()
}

fun Long.toFormattedDateTime() : String {
  return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault()).format(
    ReadableFormatWithTime
  )
}

fun Long.toReadableDate() : String {
  return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault()).format(
    ReadableFormat
  )
}