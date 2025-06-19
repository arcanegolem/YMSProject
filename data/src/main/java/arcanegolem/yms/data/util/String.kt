package arcanegolem.yms.data.util

internal fun String.formatCash(currency : String) : String {
  val currencySymbol = currency.formatCurrency()

  val intPart = this.split(".").first().replace("-", "")
  val floatPart = this.split(".").last()

  val digit = if (this.contains("-")) "-" else ""

  return "$digit${
    intPart
      .reversed()
      .chunked(3)
      .joinToString(" ")
      .reversed()
  }${
    if (floatPart.toInt() != 0) ".$floatPart" else ""
  } $currencySymbol"
}

internal fun String.formatCurrency() : String {
  return when (this) {
    "USD" -> "$"
    "RUB" -> "₽"
    "EUR" -> "€"
    else -> ""
  }
}