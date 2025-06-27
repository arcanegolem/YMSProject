package arcanegolem.yms.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class AccountBrief(
  val id : Int,
  val name : String,
  val balance : String,
  val currency : String
)
