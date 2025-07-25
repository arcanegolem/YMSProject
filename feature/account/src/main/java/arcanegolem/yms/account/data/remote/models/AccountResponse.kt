package arcanegolem.yms.account.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
internal data class AccountResponse(
  val id : Int,
  val name : String,
  val balance : String,
  val currency : String,
  val incomeStats : List<StatItem>,
  val expenseStats : List<StatItem>,
  val createdAt : String,
  val updatedAt : String
)
