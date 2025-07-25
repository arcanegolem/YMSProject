package arcanegolem.yms.settings.ui.settings.models

import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.haptic.BEE_BUZZ
import arcanegolem.yms.core.ui.haptic.FLY_BUZZ
import arcanegolem.yms.core.ui.haptic.MOSQUITO_SQUEAK

internal val hapticModels = listOf(
  HapticModel(
    pattern = BEE_BUZZ,
    nameResId = R.string.bee_buzz
  ),
  HapticModel(
    pattern = FLY_BUZZ,
    nameResId = R.string.fly_buzz
  ),
  HapticModel(
    pattern = MOSQUITO_SQUEAK,
    nameResId = R.string.mosquito_squeak
  )
)