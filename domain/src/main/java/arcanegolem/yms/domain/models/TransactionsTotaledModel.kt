package arcanegolem.yms.domain.models

data class TransactionsTotaledModel(
  val total : String,
  val transactions : List<TransactionModel>
)
