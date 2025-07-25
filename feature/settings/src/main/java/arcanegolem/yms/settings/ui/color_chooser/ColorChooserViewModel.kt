package arcanegolem.yms.settings.ui.color_chooser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.settings.domain.usecases.GetColorsUseCase
import arcanegolem.yms.settings.domain.usecases.SetColorsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ColorChooserViewModel @Inject constructor(
  private val getColorsUseCase: GetColorsUseCase,
  private val setColorsUseCase: SetColorsUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<ColorChooserState>(ColorChooserState.Idle)
  val state get() = _state.asStateFlow()
  
  fun processEvent(event: ColorChooserEvent) {
    when (event) {
      ColorChooserEvent.LoadInitial -> loadInitial()
      is ColorChooserEvent.SetColors -> setColors(event.primaryHex, event.secondaryHex)
    }
  }
  
  private fun setColors(primaryHex : Long, secondaryHex : Long) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        setColorsUseCase.execute(primaryHex, secondaryHex)
      }
    }
  }
  
  private var colorsLoadingJob : Job? = null
  
  private fun loadInitial() {
    colorsLoadingJob?.cancel()
    colorsLoadingJob = null
    
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { ColorChooserState.Loading }
        runCatching { getColorsUseCase.execute(0L, 0L) }
          .onSuccess { result ->
            colorsLoadingJob = launch {
              result.collectLatest { (primary, _) ->
                _state.update { ColorChooserState.Target(selectedPrimaryColorHex = primary) }
              }
            }
          }
          .onFailure { error ->
            _state.update {
              ColorChooserState.Error(
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