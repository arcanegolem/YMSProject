package arcanegolem.yms.graph.charts.bar

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color

data class BarChartItem(
  @param:FloatRange(from = 0.0, to = 1.0) val scaledToMaxValue : Float,
  val xAxisLabel : String,
  val color : Color
)
