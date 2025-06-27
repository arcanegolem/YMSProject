package arcanegolem.yms.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class Category(
  val id : Int,
  val name : String,
  val emoji : String,
  val isIncome : Boolean
)
