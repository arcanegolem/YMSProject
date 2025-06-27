package arcanegolem.yms.project.ui.screens.incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.project.util.network.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomesViewModel(
  private val loadIncomesUseCase: LoadIncomesUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow<IncomesState>(IncomesState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : IncomesEvent) {
    when (event) {
      is IncomesEvent.LoadIncomes -> loadIncomes()
    }
  }

  private fun loadIncomes() {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { IncomesState.Loading }
        if (!NetworkMonitor.networkAvailable.value) return@withContext
        runCatching { loadIncomesUseCase.execute(System.currentTimeMillis(), System.currentTimeMillis()) }
          .onSuccess { result -> _state.update { IncomesState.Target(result) } }
          .onFailure { error ->
            _state.update {
              IncomesState.Error(
                YMSError(
                  R.string.transaction_error_desc,
                  error
                )
              )
            }
          }
      }
    }
  }
}
