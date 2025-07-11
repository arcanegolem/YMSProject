package arcanegolem.yms.account.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class StatItem(
  val categoryId : Int,
  val categoryName : String,
  val emoji : String,
  val amount : String
)
