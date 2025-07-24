package arcanegolem.yms.transactions.domain.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.graph.charts.pie.PieChartItem
import arcanegolem.yms.graph.charts.pie.PieChartModel

data class TransactionAnalysisModel(
  val isIncome : Boolean,
  val total : String,
  val categoriesData : List<CategoryTotalModel>,
  val periodStart : Long,
  val periodEnd : Long
)

@Composable
fun TransactionAnalysisModel.toPieChartModel(targetAmount : Int) : PieChartModel {
  val otherText = stringResource(R.string.other_text)
  
  fun String.percentageToFloat() : Float {
    return this.removeSuffix("%").replace(',', '.').toFloat()
  }
  
  fun CategoryTotalModel.getPercentageName() : String {
    return "$percentage ${category.name}"
  }
  
  return remember(this) {
    if (categoriesData.size >= targetAmount) {
      
      val remainingPercentage = categoriesData
        .slice(targetAmount..categoriesData.lastIndex)
        .map { it.percentage.percentageToFloat() }
        .sum()
      
      PieChartModel(
        items = categoriesData.slice(0 until targetAmount).map {
          PieChartItem(
            name = it.getPercentageName(),
            percentage = it.percentage.percentageToFloat()
          )
        } + PieChartItem(
          name = "${remainingPercentage}% $otherText" ,
          percentage = remainingPercentage
        )
      )
    } else {
      PieChartModel(
        items = categoriesData.map {
          PieChartItem(
            name = it.getPercentageName(),
            percentage = it.percentage.percentageToFloat()
          )
        }
      )
    }
  }
}
