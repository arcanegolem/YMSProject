package arcanegolem.yms.project.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class YMSDestinationModel<T : Any>(
  @StringRes val titleId: Int,
  @DrawableRes val iconId : Int,
  val destination : T
)
