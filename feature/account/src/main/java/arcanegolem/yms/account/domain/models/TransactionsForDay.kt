package arcanegolem.yms.account.domain.models

import arcanegolem.yms.core.domain.models.TransactionModel

data class TransactionsForDay(
  val dayOfMonth : String,
  val dayTotal : String,
  val transactions : List<TransactionModel>
)
