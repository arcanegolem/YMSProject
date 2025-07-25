package arcanegolem.yms.settings.ui.color_chooser.models

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.color.CLASSY_PURPLE_HEX
import arcanegolem.yms.core.ui.color.CLASSY_PURPLE_SECONDARY_HEX
import arcanegolem.yms.core.ui.color.ELDORADO_HEX
import arcanegolem.yms.core.ui.color.ELDORADO_SECONDARY_HEX
import arcanegolem.yms.core.ui.color.GREEN_MAIN_HEX
import arcanegolem.yms.core.ui.color.GREEN_SECONDARY_HEX

internal val colorModels = listOf(
  ColorModel(
    primaryHex = GREEN_MAIN_HEX,
    secondaryHex = GREEN_SECONDARY_HEX,
    nameResId = R.string.money_green_text
  ),
  ColorModel(
    primaryHex = CLASSY_PURPLE_HEX,
    secondaryHex = CLASSY_PURPLE_SECONDARY_HEX,
    nameResId = R.string.classy_purple_text
  ),
  ColorModel(
    primaryHex = ELDORADO_HEX,
    secondaryHex = ELDORADO_SECONDARY_HEX,
    nameResId = R.string.eldorado_text
  )
)