package arcanegolem.yms.settings.navigation

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.bottom_bar.YMSDestinationModel

object SettingsDestinationModel {
  operator fun invoke() : YMSDestinationModel<Any> {
    return YMSDestinationModel(
      titleId = R.string.settings_label,
      iconId = R.drawable.settings,
      destination = SettingsGraph
    )
  }
}