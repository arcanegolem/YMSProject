package arcanegolem.yms.graph.charts.pie

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.graph.util.generatePieChartColors

@Composable
fun PieChart(
  modifier: Modifier = Modifier,
  model : PieChartModel,
  radius : Dp,
  barWidth : Dp = PieChartDefaults.DEFAULT_CHART_BAR_WIDTH,
  sizeAnimationSpec : PieChartAnimationSpec = PieChartDefaults.defaultSizeAnimationSpec,
  targetAmount : Int = 5
) {
  var triggerAnimationFlag by remember { mutableStateOf(false) }
  var textVisible by remember { mutableStateOf(false) }
  val targetChartSize = remember { radius * 2 }
  val chartSize by animateDpAsState(
    targetValue = if (triggerAnimationFlag) targetChartSize else radius,
    animationSpec = tween(
      durationMillis = sizeAnimationSpec.durationMillis,
      delayMillis = sizeAnimationSpec.delayMillis,
      easing = sizeAnimationSpec.easing
    )
  )
  val baseColor = MaterialTheme.colorScheme.primary
  val chartTextStyle = MaterialTheme.typography.bodySmall.copy(
    fontSize = 8.sp,
    lineHeight = 10.sp,
  )
  
  val colors = remember(model) {
    generatePieChartColors(
      baseColor = baseColor,
      count = targetAmount + 1
    )
  }
  
  val textMeasurer = rememberTextMeasurer()
  
  
  LaunchedEffect(chartSize) {
    if (chartSize > targetChartSize / 1.5f) {
      textVisible = true
    }
  }
  LaunchedEffect(Unit) {
    triggerAnimationFlag = true
  }
  
  Box(
    modifier = modifier
  ) {
    Canvas(
      modifier = Modifier
        .align(Alignment.Center)
        .size(chartSize),
      contentDescription = "PieChart"
    ) {
      var trailDegrees = -90f
      
      val diameterSplit = 3.5f
      val dotRadius = 3.dp.toPx()
      val dotSpacing = dotRadius * 2
      
      var textYOffset : Float = size.height / model.items.size
      val textXOffset = size.width / diameterSplit
      val textMaxWidth = ((size.width / diameterSplit) * 2 - dotRadius - dotSpacing).toInt()
      
      model.items.forEachIndexed { idx, chartItem ->
        val sweep = 360f * (chartItem.percentage / 100f)
        
        drawArc(
          color = colors[idx],
          startAngle = trailDegrees,
          sweepAngle = sweep,
          useCenter = false,
          style = Stroke(width = barWidth.toPx())
        )
        
        val textLayoutResult = textMeasurer.measure(
          text = chartItem.name,
          style = chartTextStyle,
          constraints = Constraints(
            maxWidth = textMaxWidth,
          ),
          overflow = TextOverflow.Ellipsis,
          maxLines = 2
        )
        
        if (textVisible) {
          drawCircle(
            color = colors[idx],
            radius = dotRadius,
            center = Offset(
              x = textXOffset,
              y = textYOffset + textLayoutResult.size.height / 2
            )
          )
          
          drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
              x = textXOffset + dotRadius + dotSpacing,
              y = textYOffset
            )
          )
        }
        
        textYOffset += textLayoutResult.size.height
        
        trailDegrees += sweep
      }
    }
  }
}