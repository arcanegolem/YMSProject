package arcanegolem.yms.project.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.GetAccountUseCase
import arcanegolem.yms.domain.usecases.LoadAccountRemoteUseCase
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.project.util.network.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
  private val getAccountUseCase: GetAccountUseCase,
  private val loadAccountRemoteUseCase: LoadAccountRemoteUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<AccountState>(AccountState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : AccountEvent) {
    when (event) {
      AccountEvent.LoadAccount -> loadAccount()
    }
  }

  private fun loadAccount() {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { AccountState.Loading }
        if (NetworkMonitor.networkAvailable.value) {
          runCatching{ loadAccountRemoteUseCase.execute() }
            .onSuccess {
              launch { loadCached() }
            }
            .onFailure { error ->
              _state.update {
                AccountState.Error(
                  YMSError(
                    R.string.account_error_desc,
                    error
                  )
                )
              }
            }
        } else {
          launch { loadCached() }
        }
      }
    }
  }

  private suspend fun loadCached() {
    runCatching {
      getAccountUseCase.execute()
    }.onSuccess { result ->
      result.collect { accountModel ->
        accountModel?.let {
          _state.update {
            AccountState.Target(accountModel)
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
