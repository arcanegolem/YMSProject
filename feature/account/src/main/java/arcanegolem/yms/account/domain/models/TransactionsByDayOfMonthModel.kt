package arcanegolem.yms.account.domain.models

data class TransactionsByDayOfMonthModel(
  val year : String,
  val month : String,
  val transactionsByDay : List<TransactionsForDay>
)
