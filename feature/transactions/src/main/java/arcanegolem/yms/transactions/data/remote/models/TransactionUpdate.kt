package arcanegolem.yms.transactions.data.remote.models

import arcanegolem.yms.core.data.remote.helpers.PayloadObject
import kotlinx.serialization.Serializable

@Serializable
data class TransactionUpdate(
  val transactionId : Int,
  val accountId : Int,
  val categoryId : Int,
  val amount : String,
  val transactionDate : String,
  val comment : String? = null
) : PayloadObject
