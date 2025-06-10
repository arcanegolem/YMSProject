package arcanegolem.yms.project.util

fun String.isEmoji() : Boolean {
  return !matches("[a-zA-Z0-9 а-яА-Я]*\$".toRegex())
}