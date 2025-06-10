package arcanegolem.yms.project.incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.data.mock.mockAccount
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        delay(600) // Для демонстрации стейта загрузки
        val result = loadIncomesUseCase.execute(mockAccount.id, mockAccount.currency)
        _state.update { IncomesState.Target(result) }
      }
    }
  }
}