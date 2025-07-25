package arcanegolem.yms.settings.ui.settings

sealed class SettingsEvent {
  data object LoadInitial : SettingsEvent()
  data class SetDarkThemeFlag(val new : Boolean) : SettingsEvent()
  data class SetHapticEnabled(val new : Boolean) : SettingsEvent()
  data class SetHapticPattern(val new : LongArray) : SettingsEvent() {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false
      
      other as SetHapticPattern
      
      if (!new.contentEquals(other.new)) return false
      
      return true
    }
    
    override fun hashCode(): Int {
      return new.contentHashCode()
    }
  }
}