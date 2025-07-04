package arcanegolem.yms.project.ui.screens.account_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.usecases.UpdateAccountUseCase
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.project.util.formatCorrectAndSign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountEditViewModel(
  private val updateAccountUseCase : UpdateAccountUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<AccountEditState>(AccountEditState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event: AccountEditEvent) {
    when (event) {
      is AccountEditEvent.ConsumeUpdates -> {
        val s = _state.value
        if (s is AccountEditState.Target) updateAccount(s.result, event.onSuccess)
      }
      is AccountEditEvent.InitialLoad -> {
        _state.update {
          AccountEditState.Target(AccountModel(event.name, event.balance, event.currency))
        }
      }
      is AccountEditEvent.UpdateAccountBalance -> updateBalance(event.new)
      is AccountEditEvent.UpdateAccountCurrency -> updateCurrency(event.new)
      is AccountEditEvent.UpdateAccountName -> updateName(event.new)
    }
  }

  private fun updateBalance(new : String) {
    val s = _state.value
    if (s is AccountEditState.Target) {
      _state.update {
        s.copy(result = s.result.copy(balance = new.formatCorrectAndSign()))
      }
    }
  }

  private fun updateCurrency(new : String) {
    val s = _state.value
    if (s is AccountEditState.Target) {
      _state.update {
        s.copy(result = s.result.copy(currency = new))
      }
    }
  }

  private fun updateName(new: String) {
    val s = _state.value
    if (s is AccountEditState.Target) {
      _state.update {
        s.copy(result = s.result.copy(name = new))
      }
    }
  }

  private fun updateAccount(model : AccountModel, onSuccess : () -> Unit) {
    viewModelScope.launch {
      withContext(Dispatchers.IO){
        runCatching {
          updateAccountUseCase.execute(model)
        }.onFailure { error ->
          _state.update {
            AccountEditState.Error(
              YMSError(
                R.string.account_update_error_desc,
                error
              )
            )
          }
        }.onSuccess { withContext(Dispatchers.Main) { onSuccess() } }
      }
    }
  }
}