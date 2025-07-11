package arcanegolem.yms.transactions.domain.models

data class TransactionHistoryModel(
  val isIncome : Boolean,
  val transactionsTotaled : TransactionsTotaledModel,
  val periodStart : Long,
  val periodEnd : Long
)
