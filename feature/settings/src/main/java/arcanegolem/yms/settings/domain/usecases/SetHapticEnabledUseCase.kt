package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.settings.domain.repos.SettingsRepository
import javax.inject.Inject

class SetHapticEnabledUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  suspend fun execute(new : Boolean) {
    settingsRepository.setHapticEnabled(new)
  }
}