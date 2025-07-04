package arcanegolem.yms.data.util

internal fun String.formatCash(currency : String) : String {
  val currencySymbol = currency.formatCurrency()

  val intPart = this.split(".").first().replace("-", "")
  val floatPart = if (this.split(".").size != 1)this.split(".").last() else ""

  val digit = if (this.contains("-")) "-" else ""

  return "$digit${
    intPart
      .reversed()
      .chunked(3)
      .joinToString(" ")
      .reversed()
  }${
    if (floatPart.toIntOrNull() != 0 && floatPart.toIntOrNull() != null) ".$floatPart" else ""
  } $currencySymbol"
}

internal fun String.formatCashBackwards() : String {
  val clean = this.replace(Regex("[^-\\d.]"), "")
  return clean
}

internal fun String.formatCurrency() : String {
  return when (this) {
    "USD" -> "$"
    "RUB" -> "₽"
    "EUR" -> "€"
    else -> ""
  }
}

internal fun String.formatCurrencyBackwards() : String {
  return when (this) {
    "$" -> "USD"
    "₽" -> "RUB"
    "€" -> "EUR"
    else -> ""
  }
}
