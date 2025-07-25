package arcanegolem.yms.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.core.utils.containers.Quad
import arcanegolem.yms.settings.di.BuildInfo
import arcanegolem.yms.settings.domain.usecases.GetDarkThemeUseCase
import arcanegolem.yms.settings.domain.usecases.GetHapticEnabledUseCase
import arcanegolem.yms.settings.domain.usecases.GetHapticPatternUseCase
import arcanegolem.yms.settings.domain.usecases.GetLastSyncUseCase
import arcanegolem.yms.settings.domain.usecases.SetDarkThemeUseCase
import arcanegolem.yms.settings.domain.usecases.SetHapticEnabledUseCase
import arcanegolem.yms.settings.domain.usecases.SetHapticPatternUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
  private val buildInfo: BuildInfo,
  private val getLastSyncUseCase: GetLastSyncUseCase,
  private val getDarkThemeUseCase: GetDarkThemeUseCase,
  private val setDarkThemeUseCase: SetDarkThemeUseCase,
  private val getHapticEnabledUseCase: GetHapticEnabledUseCase,
  private val setHapticEnabledUseCase: SetHapticEnabledUseCase,
  private val getHapticPatternUseCase: GetHapticPatternUseCase,
  private val setHapticPatternUseCase: SetHapticPatternUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<SettingsState>(SettingsState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : SettingsEvent) {
    when (event) {
      SettingsEvent.LoadInitial -> loadInitial()
      is SettingsEvent.SetDarkThemeFlag -> setDarkTheme(event.new)
      is SettingsEvent.SetHapticEnabled -> setHapticEnabled(event.new)
      is SettingsEvent.SetHapticPattern -> setHapticPattern(event.new)
    }
  }
  
  private fun setDarkTheme(value : Boolean) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        setDarkThemeUseCase.execute(value)
      }
    }
  }
  
  private fun setHapticEnabled(value : Boolean) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        setHapticEnabledUseCase.execute(value)
      }
    }
  }
  
  private fun setHapticPattern(value : LongArray) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        setHapticPatternUseCase.execute(value)
      }
    }
  }
  
  private var settingsLoadingJob : Job? = null
  
  private fun loadInitial() {
    settingsLoadingJob?.cancel()
    settingsLoadingJob = null
    
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { SettingsState.Loading }
        runCatching {
          combine(
            getLastSyncUseCase.execute(),
            getDarkThemeUseCase.execute(),
            getHapticEnabledUseCase.execute(),
            getHapticPatternUseCase.execute()
          ) { lastSyncInfo, darkThemeFlag, hapticEnabled, hapticPattern ->
            Quad(lastSyncInfo, darkThemeFlag, hapticEnabled, hapticPattern)
          }
        }
          .onSuccess { result ->
            settingsLoadingJob = launch {
              result.collect { (lastSync, darkTheme, hapticEnabled, hapticPattern) ->
                _state.update {
                  SettingsState.Target(
                    lastSyncInfo = lastSync,
                    darkThemeFlag = darkTheme,
                    hapticEnabled = hapticEnabled,
                    selectedHapticPattern = hapticPattern,
                    appVersion = buildInfo.appVersion
                  )
                }
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