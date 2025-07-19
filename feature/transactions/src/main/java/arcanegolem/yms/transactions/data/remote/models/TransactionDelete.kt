package arcanegolem.yms.transactions.data.remote.models

import arcanegolem.yms.core.data.remote.helpers.PayloadObject
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDelete(
  val transactionId : Int
) : PayloadObject
