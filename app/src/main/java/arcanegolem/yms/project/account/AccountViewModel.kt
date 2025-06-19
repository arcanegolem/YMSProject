package arcanegolem.yms.project.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadFirstAccountUseCase
import arcanegolem.yms.project.util.network.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(
  private val loadAccountUseCase: LoadFirstAccountUseCase
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
        if (!NetworkMonitor.networkAvailable.value) return@withContext
        runCatching { loadAccountUseCase.execute() }
          .onSuccess { result -> _state.update { AccountState.Target(result) } }
          .onFailure { error -> _state.update { AccountState.Error(error) } }
      }
    }
  }
}