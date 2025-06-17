package arcanegolem.yms.project.history

sealed class HistoryEvent {
  data class LoadTransactionsForPeriod(
    val accountId : Int,
    val isIncome : Boolean,
    val currency : String
  ) : HistoryEvent()
}