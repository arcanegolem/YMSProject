package arcanegolem.yms.account.ui.account_edit.components.currency_chooser

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CurrencyModel(
  @param:DrawableRes val iconId : Int,
  @param:StringRes val stringRepresentationId : Int,
  val symbol : String
)
