package arcanegolem.yms.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionResponse(
  val id : Int,
  val account : AccountBrief,
  val category : Category,
  val amount : String,
  val transactionDate : String,
  val comment : String? = null,
  val createdAt : String,
  val updatedAt : String
)
