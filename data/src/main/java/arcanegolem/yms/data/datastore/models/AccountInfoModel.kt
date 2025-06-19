package arcanegolem.yms.data.datastore.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountInfoModel(
  val id : Int,
  val currency : String
)
