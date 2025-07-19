package arcanegolem.yms.transactions.ui.analysis

import arcanegolem.yms.transactions.ui.history.components.DatePickerSource

sealed class AnalysisEvent {
  data class LoadAnalysisForPeriod(
    val isIncome : Boolean,
    val periodStart : Long? = null,
    val periodEnd : Long? = null
  ) : AnalysisEvent()

  data class ChangePeriodSideAndLoadData(
    val source : DatePickerSource,
    val millis: Long? = null
  ) : AnalysisEvent()
}