package arcanegolem.yms.settings.data.repos

import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.settings.domain.repos.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SettingsRepositoryImpl(
  private val dataStoreManager: DataStoreManager
) : SettingsRepository {
  override fun getLastSync(): Flow<String> {
    return dataStoreManager.getLastSyncFlow()
  }
  
  override fun getDarkTheme(): Flow<Boolean> {
    return dataStoreManager.getDarkThemeFlagFlow()
  }
  
  override suspend fun setDarkTheme(new: Boolean) {
    dataStoreManager.setDarkTheme(new)
  }
  
  override suspend fun setColors(primary: Long, secondary: Long) {
    dataStoreManager.setPrimaryColor(primary)
    dataStoreManager.setSecondaryColor(secondary)
  }
  
  override fun getColors(defaultPrimary: Long, defaultSecondary: Long): Flow<Pair<Long, Long>> {
    return combine(
      dataStoreManager.getPrimaryColor(defaultPrimary),
      dataStoreManager.getSecondaryColor(defaultSecondary)
    ) { primary, secondary ->
      Pair(primary, secondary)
    }
  }
  
  override fun getHapticEnabled(): Flow<Boolean> {
    return dataStoreManager.getHapticEnabled()
  }
  
  override suspend fun setHapticEnabled(new: Boolean) {
    dataStoreManager.setHapticEnabled(new)
  }
  
  override fun getHapticPattern(defaultValue : LongArray): Flow<LongArray> {
    return dataStoreManager.getHapticPattern(defaultValue)
  }
  
  override suspend fun setHapticPattern(new: LongArray) {
    dataStoreManager.setHapticPattern(new)
  }
}