package arcanegolem.yms.settings.ui.settings

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError

sealed class SettingsState {
  data object Idle : SettingsState()
  data object Loading : SettingsState()
  data class Target(
    val appVersion : String,
    val lastSyncInfo : String,
    val darkThemeFlag : Boolean,
    val hapticEnabled : Boolean,
    val selectedHapticPattern : LongArray
  ) : SettingsState() {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false
      
      other as Target
      
      if (darkThemeFlag != other.darkThemeFlag) return false
      if (hapticEnabled != other.hapticEnabled) return false
      if (appVersion != other.appVersion) return false
      if (lastSyncInfo != other.lastSyncInfo) return false
      if (!selectedHapticPattern.contentEquals(other.selectedHapticPattern)) return false
      
      return true
    }
    
    override fun hashCode(): Int {
      var result = darkThemeFlag.hashCode()
      result = 31 * result + hapticEnabled.hashCode()
      result = 31 * result + appVersion.hashCode()
      result = 31 * result + lastSyncInfo.hashCode()
      result = 31 * result + selectedHapticPattern.contentHashCode()
      return result
    }
    
  }
  
  data class Error(val error : YMSError) : SettingsState()
}