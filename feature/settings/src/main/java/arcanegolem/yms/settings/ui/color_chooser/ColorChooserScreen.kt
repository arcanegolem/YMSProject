package arcanegolem.yms.settings.ui.color_chooser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.settings.ui.color_chooser.state_handlers.TargetColorChooserState

@Composable
fun ColorChooserScreen(
  state : State<ColorChooserState>,
  eventProcessor : (ColorChooserEvent) -> Unit
) {
  when (val s = state.value) {
    ColorChooserState.Idle -> eventProcessor(ColorChooserEvent.LoadInitial)
    ColorChooserState.Loading -> LoadingState()
    is ColorChooserState.Target -> TargetColorChooserState(s, eventProcessor)
    is ColorChooserState.Error -> ErrorState(s.error)
  }
}