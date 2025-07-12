package arcanegolem.yms.transactions.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionCreate(
  val accountId : Int,
  val categoryId : Int,
  val amount : String,
  val transactionDate : String,
  val comment : String? = null
)

