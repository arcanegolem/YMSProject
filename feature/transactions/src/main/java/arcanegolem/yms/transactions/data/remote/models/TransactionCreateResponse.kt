package arcanegolem.yms.transactions.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionCreateResponse(
  val id : Int,
  val accountId : Int,
  val categoryId : Int,
  val amount : String,
  val transactionDate : String,
  val comment : String? = null,
  val createdAt : String,
  val updatedAt : String
)