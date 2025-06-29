package arcanegolem.yms.project.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadHistoryUseCase
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.project.ui.components.DatePickerSource
import arcanegolem.yms.project.util.network.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
  private val loadHistoryUseCase: LoadHistoryUseCase
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
            DatePickerSource.PERIOD_START -> loadHistory(s.result.isIncome, event.millis, s.result.periodEnd)
            DatePickerSource.PERIOD_END -> loadHistory(s.result.isIncome, s.result.periodStart, event.millis)
          }
        }
      }
    }
  }

  private fun loadHistory(
    isIncome : Boolean,
    periodStart : Long?,
    periodEnd : Long?
  ) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { HistoryState.Loading }
        if (!NetworkMonitor.networkAvailable.value) return@withContext
        runCatching { loadHistoryUseCase.execute(isIncome, periodStart, periodEnd) }
          .onSuccess { result -> _state.update { HistoryState.Target(result) } }
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
