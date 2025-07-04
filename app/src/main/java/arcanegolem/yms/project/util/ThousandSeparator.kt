package arcanegolem.yms.project.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

private const val GROUPING_SYMBOL = ' '
private const val DECIMAL_SYMBOL = '.'

/**
 * Кастомный вариант [VisualTransformation] для числа с плавающей точкой
 */
internal val VisualTransformation.Companion.ThousandSeparator
  @Composable
  get() = remember(::ThousandSeparatorTransform)

/**
 * Кастомный вариант [VisualTransformation] для числа с плавающей точкой
 */
internal class ThousandSeparatorTransform : VisualTransformation {
  override fun filter(text: AnnotatedString): TransformedText {
    val string = text.text
    val integer = string.substringBefore(DECIMAL_SYMBOL)
    val output = string.withSeparator(
      separator = GROUPING_SYMBOL,
      length = integer.length,
    )
    return TransformedText(
      text = AnnotatedString(output),
      offsetMapping = SeparatorMapping(integer),
    )
  }
}

/**
 * Маппинг оффсетов для [ThousandSeparator]
 *
 * @param segmentSize длина сегмента после которого ставится сепаратор
 * @param startOffset длина стартогвого сегмента
 * @param separatorCount количество сепараторов
 */
private class SeparatorMapping(
  private val text: String,
  private val segmentSize: Int = 3,
  startOffset: Int = text.lastIndex % segmentSize + 1,
  separatorCount: Int = text.lastIndex / segmentSize,
) : OffsetMapping {
  private val padding = segmentSize - startOffset
  private val transformedTextLength = text.length + separatorCount

  override fun originalToTransformed(offset: Int): Int {
    val intOffset = offset.coerceAtMost(text.length)
    val offsetSeparatorCount = (intOffset + padding - 1) / segmentSize
    val decimalOffset = (offset - text.length).coerceAtLeast(0)
    return intOffset + offsetSeparatorCount + decimalOffset
  }

  override fun transformedToOriginal(offset: Int): Int {
    val intOffset = offset.coerceAtMost(transformedTextLength)
    val offsetSeparatorCount = (intOffset + padding) / (segmentSize + 1)
    val decimalOffset = (offset - transformedTextLength).coerceAtLeast(0)
    return intOffset - offsetSeparatorCount + decimalOffset
  }
}

/**
 * Создает вариант строки с сепараторами
 *
 * @param separator символ сепаратора
 * @param length общее количество сегментов которое нужно разделить
 * @param segmentSize размер сегмента
 * @param startOffset длина стартогвого сегмента
 * @param separatorCount количество сепараторов
 *
 * @return строку с сепараторами прим. 1234 > 1[[separator]]234
 */
private fun String.withSeparator(
  separator: Char,
  length: Int = this.length,
  segmentSize: Int = 3,
  startOffset: Int = (length - 1) % segmentSize + 1,
  separatorCount: Int = (length - 1) / segmentSize,
) = buildString {
  append(this@withSeparator)
  repeat(separatorCount) { index ->
    val offset = startOffset + index * (segmentSize + 1)
    insert(offset, separator)
  }
}