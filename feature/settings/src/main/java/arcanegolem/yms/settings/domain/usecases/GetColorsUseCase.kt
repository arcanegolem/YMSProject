package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.settings.domain.repos.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetColorsUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  fun execute(defaultPrimary : Long, defaultSecondary : Long): Flow<Pair<Long, Long>> {
    return settingsRepository.getColors(defaultPrimary, defaultSecondary)
  }
}