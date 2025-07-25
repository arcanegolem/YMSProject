package arcanegolem.yms.settings.domain.usecases

import arcanegolem.yms.core.ui.haptic.BEE_BUZZ
import arcanegolem.yms.settings.domain.repos.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHapticPatternUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
  fun execute(defaultValue : LongArray = BEE_BUZZ) : Flow<LongArray> {
    return settingsRepository.getHapticPattern(defaultValue)
  }
}