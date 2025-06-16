package arcanegolem.yms.data.models

import kotlinx.serialization.Serializable

@Serializable
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
