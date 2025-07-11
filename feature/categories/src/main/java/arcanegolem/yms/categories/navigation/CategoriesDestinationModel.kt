package arcanegolem.yms.categories.navigation

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.bottom_bar.YMSDestinationModel

object CategoriesDestinationModel {
  operator fun invoke() : YMSDestinationModel<Any> {
    return YMSDestinationModel(
      titleId = R.string.categories_label,
      iconId = R.drawable.categories,
      destination = CategoriesGraph
    )
  }
}