package arcanegolem.yms.account.ui.account.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import arcanegolem.yms.account.domain.models.TransactionsByDayOfMonthModel
import arcanegolem.yms.core.data.utils.formatCashBackwards
import arcanegolem.yms.core.ui.color.NegativeColor
import arcanegolem.yms.core.ui.color.PositiveColor
import arcanegolem.yms.graph.charts.bar.BarChartItem
import arcanegolem.yms.graph.charts.bar.BarChartModel
import kotlin.math.abs

@Composable
fun TransactionsByDayOfMonthModel.toBarChartModel() : BarChartModel {
  val maxDayTotal = remember(this) { this.transactionsByDay.maxOfOrNull {
    abs(it.dayTotal.formatCashBackwards().toFloat())
  } }
  
  return remember(this) {
    BarChartModel(
      items = this.transactionsByDay.map {
        BarChartItem(
          scaledToMaxValue = abs(it.dayTotal.formatCashBackwards().toFloat()) / (maxDayTotal ?: 1f),
          xAxisLabel = it.dayOfMonth,
          color = if (it.dayTotal.formatCashBackwards().toFloat() >= 0) PositiveColor else NegativeColor
        )
      }
    )
  }
}