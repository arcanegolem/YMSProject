package arcanegolem.yms.transactions.ui.expenses

sealed class ExpensesEvent {
  data object LoadExpenses : ExpensesEvent()
}
