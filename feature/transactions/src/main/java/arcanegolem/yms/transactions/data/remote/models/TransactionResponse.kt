package arcanegolem.yms.transactions.data.remote.models

import arcanegolem.yms.core.data.remote.models.AccountBrief
import arcanegolem.yms.core.data.remote.models.Category
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
