package arcanegolem.yms.domain.models

data class TransactionModel(
  val id : Int,
  val emoji : String,
  val label : String,
  val comment : String?,
  val amountFormatted : String,
  val dateTimeMillis : Long
)
