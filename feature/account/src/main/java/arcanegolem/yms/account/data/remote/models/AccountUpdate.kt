package arcanegolem.yms.account.data.remote.models

import arcanegolem.yms.core.data.remote.helpers.PayloadObject
import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdate(
  val name : String,
  val balance : String,
  val currency : String
) : PayloadObject
