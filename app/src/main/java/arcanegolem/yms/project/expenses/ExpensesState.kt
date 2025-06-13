package arcanegolem.yms.project.expenses

import arcanegolem.yms.domain.models.TransactionsTotaledModel

sealed class ExpensesState {
  data object Idle : ExpensesState()
  data object Loading : ExpensesState()
  data class Target(val result : TransactionsTotaledModel) : ExpensesState()
  data class Error(val error : Throwable) : ExpensesState()
}