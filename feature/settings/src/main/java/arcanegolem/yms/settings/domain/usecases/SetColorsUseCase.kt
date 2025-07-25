package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.settings.domain.repos.SettingsRepository
import javax.inject.Inject

class SetColorsUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  suspend fun execute(primary : Long, secondary : Long) {
    settingsRepository.setColors(primary, secondary)
  }
}