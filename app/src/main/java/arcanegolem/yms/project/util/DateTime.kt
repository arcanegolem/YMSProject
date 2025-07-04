package arcanegolem.yms.project.util

import androidx.compose.ui.text.intl.LocaleList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private val RUSSIAN_FULL = listOf(
  "Январь",
  "Февраль",
  "Март",
  "Апрель",
  "Май",
  "Июнь",
  "Июль",
  "Август",
  "Сентябрь",
  "Октябрь",
  "Ноябрь",
  "Декабрь"
)

fun getMonthNames() : MonthNames {
  val lang = LocaleList.current.localeList[0].language

  return when (lang) {
    "en" -> MonthNames.ENGLISH_FULL
    "ru" -> MonthNames(RUSSIAN_FULL)
    else -> MonthNames.ENGLISH_ABBREVIATED
  }
}

private val ReadableFormat = LocalDateTime.Format {
  day(padding = Padding.ZERO)
  char(' ')
  monthName(getMonthNames())
  char(' ')
  year()
}

private val ReadableFormatWithTime = LocalDateTime.Format {
  day(padding = Padding.ZERO)
  char('.')
  monthNumber()
  char('.')
  year()
  char(' ')
  hour()
  char(':')
  minute()
}

@OptIn(ExperimentalTime::class)
fun Long.toFormattedDateTime() : String {
  return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault()).format(
    ReadableFormatWithTime
  )
}

@OptIn(ExperimentalTime::class)
fun Long.toReadableDate() : String {
  return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault()).format(
    ReadableFormat
  )
}
