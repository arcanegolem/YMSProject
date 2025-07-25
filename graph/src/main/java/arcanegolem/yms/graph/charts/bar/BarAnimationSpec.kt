package arcanegolem.yms.graph.charts.bar

data class BarAnimationSpec(
  val durationMillis : Int,
  val delayRelativeToIndexMillis : (index : Int) -> Int
)
