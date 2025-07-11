package arcanegolem.yms.account.navigation

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.bottom_bar.YMSDestinationModel

object AccountDestinationModel {
  operator fun invoke() : YMSDestinationModel<Any> {
    return YMSDestinationModel(
      titleId = R.string.account_label,
      iconId = R.drawable.cash_account,
      destination = AccountGraph
    )
  }
}