package arcanegolem.yms.graph.charts.pie

import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.ui.unit.dp

object PieChartDefaults {
  private const val DEFAULT_DURATION = 1000
  private const val DEFAULT_DELAY = 0
  
  val DEFAULT_CHART_BAR_WIDTH = 8.dp
  
  val defaultSizeAnimationSpec = PieChartAnimationSpec(
    durationMillis = DEFAULT_DURATION,
    delayMillis = DEFAULT_DELAY,
    easing = EaseInOutElastic
  )
}