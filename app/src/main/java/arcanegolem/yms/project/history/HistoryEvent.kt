package arcanegolem.yms.project.history

sealed class HistoryEvent {
  data class LoadTransactionsForPeriod(
    val isIncome : Boolean,
    val periodStart : Long? = null,
    val periodEnd : Long? = null
  ) : HistoryEvent()
}