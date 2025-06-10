package arcanegolem.yms.project.util

fun String.isAllowedText() : Boolean {
  return matches("[a-zA-Z0-9 а-яА-Я]*\$".toRegex())
}

fun String.formatCash(currency : String) : String {
  val currencySymbol = currency.formatCurrency()

  val intPart = this.split(".").first()
  val floatPart = this.split(".").last()

  return "${
    intPart
      .reversed()
      .chunked(3)
      .joinToString(" ")
      .reversed()
  }${
    if (floatPart.toInt() != 0) ".$floatPart" else ""
  } $currencySymbol"
}

fun String.formatCurrency() : String {
  return when (this) {
    "USD" -> "$"
    "RUB" -> "₽"
    "EUR" -> "€"
    else -> ""
  }
}