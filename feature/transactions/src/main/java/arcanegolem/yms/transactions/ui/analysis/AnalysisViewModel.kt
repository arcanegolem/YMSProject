package arcanegolem.yms.transactions.ui.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.transactions.domain.usecases.LoadTransactionsAnalysisUseCase
import arcanegolem.yms.transactions.ui.history.components.DatePickerSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnalysisViewModel @Inject constructor(
  private val loadTransactionsAnalysisUseCase : LoadTransactionsAnalysisUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<AnalysisState>(AnalysisState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event: AnalysisEvent) {
    when (event) {
      is AnalysisEvent.LoadAnalysisForPeriod -> loadAnalysis(event.isIncome, event.periodStart, event.periodEnd)
      is AnalysisEvent.ChangePeriodSideAndLoadData -> {
        val s = _state.value
        if (s is AnalysisState.Target) {
          when (event.source) {
            DatePickerSource.PERIOD_START -> loadAnalysis(s.result.isIncome, event.millis, s.result.periodEnd)
            DatePickerSource.PERIOD_END -> loadAnalysis(s.result.isIncome, s.result.periodStart, event.millis)
          }
        }
      }
    }
  }
  
  private var resultCollectionJob : Job? = null

  fun loadAnalysis(
    isIncome : Boolean,
    periodStart : Long?,
    periodEnd : Long?
  ) {
    resultCollectionJob?.cancel()
    resultCollectionJob = null
    
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { AnalysisState.Loading }
        runCatching { loadTransactionsAnalysisUseCase.execute(isIncome, periodStart, periodEnd) }
          .onSuccess { result ->
            resultCollectionJob = launch {
              result.collect { transactionAnalysisModel ->
                _state.update {
                  AnalysisState.Target(transactionAnalysisModel)
                }
              }
            }
          }
          .onFailure { error ->
            _state.update {
              AnalysisState.Error(
                YMSError(
                  R.string.analysis_error_desc,
                  error
                )
              )
            }
          }
      }
    }
  }
}