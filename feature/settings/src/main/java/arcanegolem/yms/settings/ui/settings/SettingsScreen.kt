package arcanegolem.yms.settings.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.settings.ui.settings.state_handlers.TargetSettingsState


@Composable
fun SettingsScreen(
  state : State<SettingsState>,
  eventProcessor : (SettingsEvent) -> Unit
) {
  when (val s = state.value) {
    SettingsState.Idle -> eventProcessor(SettingsEvent.LoadInitial)
    SettingsState.Loading -> LoadingState()
    is SettingsState.Target -> TargetSettingsState(s)
    is SettingsState.Error -> ErrorState(s.error)
  }
}
