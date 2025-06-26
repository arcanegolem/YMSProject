package arcanegolem.yms.project.ui.screens.incomes

sealed class IncomesEvent {
  data object LoadIncomes : IncomesEvent()
}