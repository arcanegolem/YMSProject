package arcanegolem.yms.project.incomes

sealed class IncomesEvent {
  data object LoadIncomes : IncomesEvent()
}