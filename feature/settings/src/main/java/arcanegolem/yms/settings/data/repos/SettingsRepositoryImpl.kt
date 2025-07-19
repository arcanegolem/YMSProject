package arcanegolem.yms.settings.data.repos

import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.settings.domain.repos.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
  private val dataStoreManager: DataStoreManager
) : SettingsRepository {
  override suspend fun getLastSync(): Flow<String> {
    return dataStoreManager.getLastSyncFlow()
  }
}