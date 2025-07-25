package arcanegolem.yms.graph.charts.bar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object BarChartDefaults {
  const val DEFAULT_BAR_ANIMATION_DURATION = 1000
  
  val colors = BarChartColors(
    axisColor = Color.LightGray,
    labelColor = Color.Black
  )
  
  val defaultBarWidth : Dp = 4.dp
  val defaultChartHeight : Dp = 300.dp
  val defaultBarShape : Shape = RoundedCornerShape(100)
  val defaultBarAnimationSpec = BarAnimationSpec(
    durationMillis = 1000,
    delayRelativeToIndexMillis = { 0 }
  )
}