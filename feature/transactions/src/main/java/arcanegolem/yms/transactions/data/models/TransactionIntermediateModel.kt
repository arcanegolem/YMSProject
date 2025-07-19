package arcanegolem.yms.transactions.data.models

data class TransactionIntermediateModel(
  val transactionId : Int,
  val categoryId : Int,
  val amount : String,
  val transactionDateMillis : Long,
  val createdAt : String,
  val updatedAt : String = "",
  val comment : String = "",
  val isIncome : Boolean,
  val isArbitrary : Boolean
)
