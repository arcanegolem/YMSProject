package arcanegolem.yms.settings.ui.color_chooser.models

import androidx.annotation.StringRes

internal data class ColorModel(
  val primaryHex : Long,
  val secondaryHex : Long,
  @param:StringRes val nameResId : Int
)
