package arcanegolem.yms.transactions.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.usecases.LoadTransactionsHistoryUseCase
import arcanegolem.yms.transactions.ui.history.components.DatePickerSource.PERIOD_END
import arcanegolem.yms.transactions.ui.history.components.DatePickerSource.PERIOD_START
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
  private val loadTransactionsHistoryUseCase: LoadTransactionsHistoryUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<HistoryState>(HistoryState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : HistoryEvent) {
    when (event) {
      is HistoryEvent.LoadTransactionsForPeriod -> loadHistory(event.isIncome, event.periodStart, event.periodEnd)
      is HistoryEvent.ChangePeriodSideAndLoadData -> {
        val s = _state.value
        if (s is HistoryState.Target) {
          when (event.source) {
            PERIOD_START -> loadHistory(s.result.isIncome, event.millis, s.result.periodEnd)
            PERIOD_END -> loadHistory(s.result.isIncome, s.result.periodStart, event.millis)
          }
        }
      }
    }
  }
  
  private var historyLoadingJob : Job? = null

  private fun loadHistory(
    isIncome : Boolean,
    periodStart : Long?,
    periodEnd : Long?
  ) {
    historyLoadingJob?.cancel()
    historyLoadingJob = null
    
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { HistoryState.Loading }
        runCatching { loadTransactionsHistoryUseCase.execute(isIncome, periodStart, periodEnd) }
          .onSuccess { result ->
            historyLoadingJob = launch {
              result.collect { transactionHistoryModel ->
                _state.update {
                  HistoryState.Target(transactionHistoryModel)
                }
              }
            }
          }
          .onFailure { error ->
            _state.update {
              HistoryState.Error(
                YMSError(
                  R.string.history_error_desc,
                  error
                )
              )
            }
          }
      }
    }
  }
}
