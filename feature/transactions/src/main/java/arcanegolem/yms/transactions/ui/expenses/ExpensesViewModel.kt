package arcanegolem.yms.transactions.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.core.utils.NetworkMonitor
import arcanegolem.yms.transactions.domain.usecases.LoadExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
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
        if (!NetworkMonitor.networkAvailable.value) return@withContext
        runCatching { loadExpensesUseCase.execute(System.currentTimeMillis(), System.currentTimeMillis())  }
          .onSuccess { result -> _state.update { ExpensesState.Target(result) } }
          .onFailure { error ->
            _state.update {
              ExpensesState.Error(
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
