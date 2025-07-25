package arcanegolem.yms.account.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.account.domain.usecases.GetAccountUseCase
import arcanegolem.yms.account.domain.usecases.LoadThisMonthTransactionsForAccountUseCase
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
  private val getAccountUseCase: GetAccountUseCase,
  private val loadThisMonthTransactionsForAccountUseCase: LoadThisMonthTransactionsForAccountUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<AccountState>(AccountState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : AccountEvent) {
    when (event) {
      AccountEvent.LoadAccount -> loadAccount()
    }
  }

  private var accountLoadingJob : Job? = null
  private var transactionsLoadingJob : Job? = null
  
  private fun loadAccount() {
    accountLoadingJob?.cancel()
    accountLoadingJob = null
    
    transactionsLoadingJob?.cancel()
    transactionsLoadingJob = null
    
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { AccountState.Loading }
        runCatching {
          getAccountUseCase.execute()
            .combine(loadThisMonthTransactionsForAccountUseCase.execute()) { am, tbm ->
              Pair(am, tbm)
            }
        }.onSuccess { result ->
          accountLoadingJob = launch {
            result.collect { (accountModel, transactionsByDayOfMonthModel) ->
              accountModel?.let {
                _state.update {
                  AccountState.Target(accountModel, transactionsByDayOfMonthModel)
                }
              }
            }
          }
        }.onFailure { error ->
          _state.update {
            AccountState.Error(
              YMSError(
                R.string.account_error_desc,
                error
              )
            )
          }
        }
      }
    }
  }
}
