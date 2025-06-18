package arcanegolem.yms.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class StatItem(
  val categoryId : Int,
  val categoryName : String,
  val emoji : String,
  val amount : String
)
