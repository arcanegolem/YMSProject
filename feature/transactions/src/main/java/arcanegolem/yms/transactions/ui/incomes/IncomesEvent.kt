package arcanegolem.yms.transactions.ui.incomes

sealed class IncomesEvent {
  data object LoadIncomes : IncomesEvent()
}
