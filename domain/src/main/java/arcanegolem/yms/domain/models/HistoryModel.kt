package arcanegolem.yms.domain.models

data class HistoryModel(
  val isIncome : Boolean,
  val transactionsTotaled : TransactionsTotaledModel,
  val periodStart : Long,
  val periodEnd : Long
)
