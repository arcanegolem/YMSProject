package arcanegolem.yms.transactions.domain.models

data class TransactionInfoModel(
  val accountName : String,
  val categoryId : Int = -1,
  val categoryName : String = "",
  val amount : String = "",
  val date : String = "",
  val time : String = "",
  val comment : String = ""
)
