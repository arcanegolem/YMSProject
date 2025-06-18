package arcanegolem.yms.project.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadExpensesUseCase
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
  private val loadExpensesUseCase : LoadExpensesUseCase,
  private val loadIncomesUseCase: LoadIncomesUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<HistoryState>(HistoryState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : HistoryEvent) {
    when (event) {
      is HistoryEvent.LoadTransactionsForPeriod -> loadHistory(event.isIncome)
    }
  }

  private fun loadHistory(
    isIncome : Boolean,
  ) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { HistoryState.Loading }
        val transactionsTotaled = if (isIncome) {
          loadIncomesUseCase.execute()
        } else {
          loadExpensesUseCase.execute()
        }
        _state.update { HistoryState.Target(transactionsTotaled) }
      }
    }
  }
}