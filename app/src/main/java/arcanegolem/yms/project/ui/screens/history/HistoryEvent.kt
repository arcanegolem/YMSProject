package arcanegolem.yms.project.ui.screens.history

import arcanegolem.yms.project.ui.components.DatePickerSource

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