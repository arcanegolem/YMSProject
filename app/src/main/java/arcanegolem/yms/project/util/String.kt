package arcanegolem.yms.project.util

fun String.isEmoji() : Boolean {
  return !matches("[a-zA-Z0-9 а-яА-Я]*\$".toRegex())
}

fun String.formatCashNoSymbol() : String {
  val clean = this.replace(Regex("[^-\\d.]"), "")
  return clean
}

fun String.formatCorrectAndSign() : String {
  val hasMinus = this.startsWith('-')
  val hasDecimal = this.contains('.')
  val clean = this.replace("-", "")

  if (!hasDecimal) {
    return if (hasMinus) "-$clean" else clean
  } else {
    val parts = clean.split('.')
    val intPart = parts[0]
    val floatPart = if (parts[1].length > 2) parts[1].substring(0..1) else parts[1]

    return if (hasMinus) "-$intPart.$floatPart" else "$intPart.$floatPart"
  }
}