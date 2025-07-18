package arcanegolem.yms.transactions.ui.incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.categories.domain.usecases.LoadCategoriesUseCase
import arcanegolem.yms.core.data.utils.dateMillisEndDay
import arcanegolem.yms.core.data.utils.dateMillisStartDay
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.usecases.LoadIncomesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IncomesViewModel @Inject constructor(
  private val loadIncomesUseCase: LoadIncomesUseCase,
  private val loadCategoriesUseCase: LoadCategoriesUseCase
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
        runCatching {
          // Костыль на предзагрузку статей
          loadCategoriesUseCase.execute()
          loadIncomesUseCase.execute(dateMillisStartDay(), dateMillisEndDay())
        }
          .onSuccess { result ->
            launch {
              result.collectLatest { transactionsTotaledModel ->
                _state.update { IncomesState.Target(transactionsTotaledModel) }
              }
            }
          }
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
