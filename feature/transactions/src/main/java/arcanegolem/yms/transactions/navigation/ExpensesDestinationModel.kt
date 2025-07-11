package arcanegolem.yms.transactions.navigation

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.bottom_bar.YMSDestinationModel

object ExpensesDestinationModel {
  operator fun invoke() : YMSDestinationModel<Any> {
    return YMSDestinationModel(
      titleId = R.string.expenses_label,
      iconId = R.drawable.expense,
      destination = ExpensesGraph
    )
  }
}