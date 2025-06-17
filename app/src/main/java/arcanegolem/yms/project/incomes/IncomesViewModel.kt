package arcanegolem.yms.project.incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase
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
        val result = loadIncomesUseCase.execute(32, "RUB", System.currentTimeMillis(), System.currentTimeMillis())
        _state.update { IncomesState.Target(result) }
      }
    }
  }
}