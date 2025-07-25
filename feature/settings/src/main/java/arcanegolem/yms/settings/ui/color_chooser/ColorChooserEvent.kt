package arcanegolem.yms.settings.ui.color_chooser

sealed class ColorChooserEvent {
  data object LoadInitial : ColorChooserEvent()
  data class SetColors(val primaryHex : Long, val secondaryHex : Long) : ColorChooserEvent()
}