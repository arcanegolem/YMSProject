package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.settings.domain.repos.SettingsRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  suspend fun execute(new : Boolean) {
    settingsRepository.setDarkTheme(new)
  }
}