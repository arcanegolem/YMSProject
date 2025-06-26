package arcanegolem.yms.data.datastore.models

import kotlinx.serialization.Serializable

@Serializable
internal data class AccountInfoModel(
  val id : Int,
  val currency : String
)
