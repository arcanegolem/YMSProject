package arcanegolem.yms.project.expenses

sealed class ExpensesEvent {
  data object LoadExpenses : ExpensesEvent()
}