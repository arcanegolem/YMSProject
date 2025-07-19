package arcanegolem.yms.settings.domain.repos

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
  suspend fun getLastSync() : Flow<String>
}