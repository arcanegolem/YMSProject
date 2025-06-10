package arcanegolem.yms.project.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.data.mock.mockAccount
import arcanegolem.yms.domain.usecases.LoadAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(
  private val loadAccountUseCase: LoadAccountUseCase
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
        delay(600) // Для демонстрации стейта загрузки
        val result = loadAccountUseCase.execute(mockAccount.id)
        _state.update { AccountState.Target(result) }
      }
    }
  }
}