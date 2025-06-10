package arcanegolem.yms.project.models

data class TransactionResponse(
  val id : Int,
  val account : AccountBrief,
  val category : Category,
  val amount : String,
  val transactionDate : String,
  val comment : String? = null,
  val createdAt : String,
  val updatedAt : String
)
