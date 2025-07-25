package arcanegolem.yms.graph.util

import androidx.compose.ui.graphics.Color
import android.graphics.Color as AndroidColor

fun Color.toHsv(): FloatArray {
  val hsv = FloatArray(3)
  AndroidColor.RGBToHSV(
    (red * 255).toInt(),
    (green * 255).toInt(),
    (blue * 255).toInt(),
    hsv
  )
  return hsv
}

fun hsvToColor(h: Float, s: Float, v: Float): Color {
  return Color(AndroidColor.HSVToColor(floatArrayOf(h, s, v)))
}

fun generatePieChartColors(baseColor: Color, count: Int): List<Color> {
  val baseHsv = baseColor.toHsv()
  val hue = baseHsv[0]
  val saturation = baseHsv[1]
  val value = baseHsv[2]
  val hueStep = 360f / count
  
  return buildList {
    repeat(count) { index ->
      val newHue = (hue + hueStep * index) % 360
      add(hsvToColor(newHue, saturation, value))
    }
  }
}