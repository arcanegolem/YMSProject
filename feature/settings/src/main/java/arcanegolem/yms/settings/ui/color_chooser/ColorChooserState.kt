package arcanegolem.yms.settings.ui.color_chooser

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError

sealed class ColorChooserState {
  data object Idle : ColorChooserState()
  data class Target(val selectedPrimaryColorHex : Long) : ColorChooserState()
  data object Loading : ColorChooserState()
  data class Error(val error : YMSError) : ColorChooserState()
}