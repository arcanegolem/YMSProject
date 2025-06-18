package arcanegolem.yms.project.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpensesViewModel(
  private val loadExpensesUseCase: LoadExpensesUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<ExpensesState>(ExpensesState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event: ExpensesEvent) {
    when (event) {
      ExpensesEvent.LoadExpenses -> loadExpenses()
    }
  }

  private fun loadExpenses() {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { ExpensesState.Loading }
        val result = loadExpensesUseCase.execute(System.currentTimeMillis(), System.currentTimeMillis())
        _state.update { ExpensesState.Target(result) }
      }
    }
  }
}