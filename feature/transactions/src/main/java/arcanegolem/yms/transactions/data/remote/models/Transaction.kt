package arcanegolem.yms.transactions.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class Transaction(
  val accountId : Int,
  val categoryId : Int,
  val transactionDate : String,
  val comment : String
)
