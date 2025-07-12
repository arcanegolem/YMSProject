package arcanegolem.yms.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Лейаут для элемента списка использовать только если нужны кастомные отступы от лейаута
 *
 * @param modifier [Modifier]
 * @param lead лид, отображается слева компонента
 * @param content основное наполнение, отображается посередине
 * @param trail трейл, отображается справа
 */
@Composable
fun YMSListItemLayout(
  modifier: Modifier,
  lead: @Composable () -> Unit = {},
  content: @Composable () -> Unit = {},
  trail: @Composable () -> Unit = {}
) {
  val internalLead: @Composable () -> Unit = { Box { lead() } }
  val internalContent: @Composable () -> Unit = { Box { content() } }
  val internalTrail: @Composable () -> Unit = { Box { trail() } }

  val padding = with(LocalDensity.current) { 16.dp.toPx() }.toInt()

  Layout(
    modifier = modifier,
    contents = listOf(internalLead, internalContent, internalTrail),
    measurePolicy = { (leadM, contentM, trailM), constraints ->
      val leadPlaceable = if (leadM.isNotEmpty()) leadM.first().measure(constraints) else null
      val trailPlaceable = if (trailM.isNotEmpty()) trailM.first().measure(constraints) else null

      val leadWidth = leadPlaceable?.measuredWidth ?: 0
      val leadHeight = leadPlaceable?.measuredHeight ?: 0
      val leadPadding = if (leadWidth == 0) 0 else padding

      val trailWidth = trailPlaceable?.measuredWidth ?: 0
      val trailHeight = trailPlaceable?.measuredHeight ?: 0
      val trailPadding = if (trailWidth == 0) 0 else padding

      val contentConstraints = constraints.copy(
        maxWidth = constraints.maxWidth - (leadWidth + leadPadding + trailWidth + trailPadding)
      )
      val contentPlaceable = if (contentM.isNotEmpty()) contentM.first().measure(contentConstraints)
      else null
      val contentWidth = contentPlaceable?.measuredWidth ?: 0
      val contentHeight = contentPlaceable?.measuredHeight ?: 0

      val heightMedian = constraints.maxHeight / 2

      layout(constraints.maxWidth, constraints.maxHeight) {
        leadPlaceable?.place(0, heightMedian - (leadHeight / 2))
        contentPlaceable?.place(leadWidth + leadPadding, heightMedian - (contentHeight / 2))
        trailPlaceable?.place(
          leadPadding + trailPadding + contentWidth + leadWidth,
          heightMedian - (trailHeight / 2)
        )
      }
    }
  )
}
