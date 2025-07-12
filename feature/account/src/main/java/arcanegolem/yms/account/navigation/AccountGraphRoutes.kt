package arcanegolem.yms.account.navigation

import kotlinx.serialization.Serializable

@Serializable
object AccountGraph

@Serializable
object Account

@Serializable
data class AccountEdit(
  val name : String,
  val balance : String,
  val currency : String
)