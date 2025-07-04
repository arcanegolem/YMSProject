package arcanegolem.yms.project.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
data class AccountEdit(
  val name : String,
  val balance : String,
  val currency : String
)