package arcanegolem.yms.transactions.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.categories.domain.usecases.LoadCategoriesUseCase
import arcanegolem.yms.core.data.utils.dateMillisEndDay
import arcanegolem.yms.core.data.utils.dateMillisStartDay
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.usecases.LoadExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
  private val loadExpensesUseCase: LoadExpensesUseCase,
  private val loadCategoriesUseCase: LoadCategoriesUseCase
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
        runCatching {
          // Костыль на предзагрузку статей
          loadCategoriesUseCase.execute()
          loadExpensesUseCase.execute(dateMillisStartDay(), dateMillisEndDay())
        }
          .onSuccess { result ->
            launch {
              result.collectLatest { transactionsTotaledModel ->
                _state.update { ExpensesState.Target(transactionsTotaledModel) }
              }
            }
          }
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
