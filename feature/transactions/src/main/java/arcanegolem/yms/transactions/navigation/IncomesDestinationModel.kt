package arcanegolem.yms.transactions.navigation

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.bottom_bar.YMSDestinationModel

object IncomesDestinationModel {
  operator fun invoke() : YMSDestinationModel<Any> {
    return YMSDestinationModel(
      titleId = R.string.incomes_label,
      iconId = R.drawable.income,
      destination = IncomesGraph
    )
  }
}