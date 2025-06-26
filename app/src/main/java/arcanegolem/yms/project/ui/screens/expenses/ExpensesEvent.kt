package arcanegolem.yms.project.ui.screens.expenses

sealed class ExpensesEvent {
  data object LoadExpenses : ExpensesEvent()
}