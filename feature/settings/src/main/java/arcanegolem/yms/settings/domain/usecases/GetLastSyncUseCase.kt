package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.settings.domain.repos.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastSyncUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  fun execute() : Flow<String> {
    return settingsRepository.getLastSync()
  }
}