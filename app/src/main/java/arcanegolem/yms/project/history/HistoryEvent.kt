package arcanegolem.yms.project.history

sealed class HistoryEvent {
  data class LoadTransactionsForPeriod(
    val isIncome : Boolean
  ) : HistoryEvent()
}