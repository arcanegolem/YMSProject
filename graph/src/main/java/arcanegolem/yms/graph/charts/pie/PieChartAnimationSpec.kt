package arcanegolem.yms.graph.charts.pie

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Stable

@Stable
data class PieChartAnimationSpec(
  val durationMillis : Int,
  val delayMillis : Int,
  val easing: Easing
)
