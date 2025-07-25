package arcanegolem.yms.graph.charts.bar

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarChart(
  modifier : Modifier = Modifier,
  model: BarChartModel,
  onBarClicked: (Int) -> Unit = {},
  chartHeight: Dp = BarChartDefaults.defaultChartHeight,
  barWidth: Dp = BarChartDefaults.defaultBarWidth,
  barShape : Shape = BarChartDefaults.defaultBarShape,
  colors: BarChartColors = BarChartDefaults.colors,
  barAnimationSpec: BarAnimationSpec = BarChartDefaults.defaultBarAnimationSpec
) {
  val xAxisScaleHeight = 40.dp
  val lineHeightXAxis = 4.dp
  val horizontalLineHeight = 2.dp
  val mainlinePadding = 4.dp
  
  Box(
    modifier = modifier,
    contentAlignment = Alignment.TopStart
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(chartHeight + xAxisScaleHeight),
      contentAlignment = Alignment.BottomCenter
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        model.items.forEachIndexed { index, barChartItem ->
          var triggerAnimationFlag by remember { mutableStateOf(false) }
          
          val barHeight by animateFloatAsState(
            targetValue = if (triggerAnimationFlag) barChartItem.scaledToMaxValue else 0f,
            animationSpec = tween(
              durationMillis = barAnimationSpec.durationMillis,
              delayMillis = barAnimationSpec.delayRelativeToIndexMillis(index),
              easing = EaseInOut
            )
          )
          LaunchedEffect(key1 = true) {
            triggerAnimationFlag = true
          }
          Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .clip(barShape)
                .width(barWidth)
                .height(chartHeight - mainlinePadding)
                .clickable {
                  onBarClicked(index)
                },
              contentAlignment = Alignment.BottomCenter
            ) {
              Box(
                modifier = Modifier
                  .clip(barShape)
                  .fillMaxWidth()
                  .fillMaxHeight(barHeight)
                  .background(barChartItem.color)
              )
            }
            Column(
              modifier = Modifier
                .padding(top = mainlinePadding)
                .height(xAxisScaleHeight),
              verticalArrangement = Arrangement.Top,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(100))
                  .width(horizontalLineHeight)
                  .height(lineHeightXAxis)
                  .background(color = colors.axisColor)
              )
              Text(
                text = barChartItem.xAxisLabel,
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = colors.labelColor
              )
            }
          }
        }
      }
      
      Column(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .padding(bottom = xAxisScaleHeight)
            .clip(RoundedCornerShape(100))
            .fillMaxWidth()
            .height(horizontalLineHeight)
            .background(color = colors.axisColor)
        )
      }
    }
  }
}