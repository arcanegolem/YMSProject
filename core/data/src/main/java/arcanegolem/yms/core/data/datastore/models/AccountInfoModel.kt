package arcanegolem.yms.core.data.datastore.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountInfoModel(
  val id : Int,
  val balance : String,
  val currency : String,
  val name : String,
  val updatedAt : String = ""
)
