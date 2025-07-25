package arcanegolem.yms.transactions.ui.incomes

import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.core.domain.models.TransactionsTotaledModel


sealed class IncomesState {
  data object Idle : IncomesState()
  data object Loading : IncomesState()
  data class Target(val result : TransactionsTotaledModel) : IncomesState()
  data class Error(val error : YMSError) : IncomesState()
}
