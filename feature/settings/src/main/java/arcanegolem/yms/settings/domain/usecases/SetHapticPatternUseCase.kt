package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.settings.domain.repos.SettingsRepository
import javax.inject.Inject

class SetHapticPatternUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  suspend fun execute(pattern : LongArray) {
    settingsRepository.setHapticPattern(pattern)
  }
}