package arcanegolem.yms.account.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class Account(
  val id : Int,
  val userId : Int,
  val name : String,
  val balance : String,
  val currency: String,
  val createdAt : String,
  val updatedAt : String
)
