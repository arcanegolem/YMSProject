package arcanegolem.yms.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.settings.domain.usecases.GetLastSyncUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
  private val getLastSyncUseCase: GetLastSyncUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<SettingsState>(SettingsState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : SettingsEvent) {
    when (event) {
      SettingsEvent.LoadInitial -> loadInitial()
    }
  }

  private fun loadInitial() {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { SettingsState.Loading }
        runCatching { getLastSyncUseCase.execute() }
          .onSuccess { result ->
            launch {
              result.collect { lastSync ->
                _state.update { SettingsState.Target(result = lastSync) }
              }
            }
          }
          .onFailure { error ->
            _state.update {
              SettingsState.Error(
                YMSError(
                  R.string.error_desc_text,
                  error
                )
              )
            }
          }
      }
    }
  }
}