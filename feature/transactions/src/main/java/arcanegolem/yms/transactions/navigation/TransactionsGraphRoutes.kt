package arcanegolem.yms.transactions.navigation

import kotlinx.serialization.Serializable

@Serializable
object IncomesGraph

@Serializable
object Incomes

@Serializable
object ExpensesGraph

@Serializable
object Expenses

@Serializable
data class History(
  val isIncome : Boolean
)