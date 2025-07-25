package arcanegolem.yms.settings.domain.repos

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
  fun getLastSync() : Flow<String>
  fun getDarkTheme() : Flow<Boolean>
  suspend fun setDarkTheme(new : Boolean)
  suspend fun setColors(primary : Long, secondary : Long)
  fun getColors(
    defaultPrimary : Long,
    defaultSecondary : Long
  ) : Flow<Pair<Long, Long>>
  fun getHapticEnabled() : Flow<Boolean>
  suspend fun setHapticEnabled(new : Boolean)
  fun getHapticPattern(defaultValue : LongArray) : Flow<LongArray>
  suspend fun setHapticPattern(new : LongArray)
}