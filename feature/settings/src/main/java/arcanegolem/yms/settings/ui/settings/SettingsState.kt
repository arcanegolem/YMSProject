package arcanegolem.yms.settings.ui.settings

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError

sealed class SettingsState {
  data object Idle : SettingsState()
  data object Loading : SettingsState()
  // Пока просто строка для последнего синка, потом будет UI модель
  data class Target(val result : String) : SettingsState()
  data class Error(val error : YMSError) : SettingsState()
}