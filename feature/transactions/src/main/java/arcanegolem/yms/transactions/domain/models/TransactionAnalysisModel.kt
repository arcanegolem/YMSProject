package arcanegolem.yms.transactions.domain.models

data class TransactionAnalysisModel(
  val isIncome : Boolean,
  val total : String,
  val categoriesData : List<CategoryTotalModel>,
  val periodStart : Long,
  val periodEnd : Long
)
