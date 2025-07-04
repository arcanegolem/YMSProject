package arcanegolem.yms.data.datastore.models

import kotlinx.serialization.Serializable

@Serializable
internal data class AccountInfoModel(
  val id : Int,
  val balance : String,
  val currency : String,
  val name : String
)
