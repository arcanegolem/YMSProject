package arcanegolem.yms.settings.ui.settings

sealed class SettingsEvent {
  data object LoadInitial : SettingsEvent()
}