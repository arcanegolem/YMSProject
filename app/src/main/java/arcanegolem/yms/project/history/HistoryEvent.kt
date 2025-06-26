package arcanegolem.yms.project.history

import arcanegolem.yms.project.history.components.DatePickerSource

sealed class HistoryEvent {
  data class LoadTransactionsForPeriod(
    val isIncome : Boolean,
    val periodStart : Long? = null,
    val periodEnd : Long? = null
  ) : HistoryEvent()

  data class ChangePeriodSideAndLoadData(
    val source : DatePickerSource,
    val millis: Long? = null
  ) : HistoryEvent()
}