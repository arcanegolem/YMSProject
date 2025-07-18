package arcanegolem.yms.transactions.ui.transaction_edit_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.categories.domain.usecases.LoadCategoriesUseCase
import arcanegolem.yms.core.data.utils.toDateStringDDMMYYYY
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import arcanegolem.yms.core.ui.components.time_picker.PickedTime
import arcanegolem.yms.core.utils.formatCorrectAndSign
import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.transactions.domain.usecases.CreateTransactionUseCase
import arcanegolem.yms.transactions.domain.usecases.DeleteTransactionUseCase
import arcanegolem.yms.transactions.domain.usecases.LoadTransactionUseCase
import arcanegolem.yms.transactions.domain.usecases.UpdateTransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionEditCreateViewModel @Inject constructor(
  private val loadCategoriesUseCase: LoadCategoriesUseCase,
  private val updateTransactionUseCase: UpdateTransactionUseCase,
  private val createTransactionUseCase: CreateTransactionUseCase,
  private val loadTransactionUseCase: LoadTransactionUseCase,
  private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<TransactionEditCreateState>(TransactionEditCreateState.Idle)
  val state get() = _state.asStateFlow()

  private var transactionId : Int? = null
  private var arbitraryFlag : Boolean? = null

  fun processEvent(event: TransactionEditCreateEvent) {
    when (event) {
      is TransactionEditCreateEvent.InitialBlank -> initializeStartingState(null,event.isIncome, null)
      is TransactionEditCreateEvent.InitialLoad -> initializeStartingState(event.transactionId, event.isIncome, event.isArbitrary)
      is TransactionEditCreateEvent.UpdateTransactionAmount -> updateAmount(event.new)
      is TransactionEditCreateEvent.UpdateTransactionCategory -> updateCategory(event.id, event.name)
      is TransactionEditCreateEvent.UpdateTransactionDate -> updateDate(event.new)
      is TransactionEditCreateEvent.UpdateTransactionComment -> updateComment(event.new)
      is TransactionEditCreateEvent.UpdateTransactionTime -> updateTime(event.new)
      is TransactionEditCreateEvent.ConsumeUpdates -> {
        val s = _state.value
        if (s is TransactionEditCreateState.Target) updateTransaction(s.result, event.onSuccess)
      }
      is TransactionEditCreateEvent.DeleteTransaction -> deleteTransaction(event.onSuccess)
    }
  }

  private fun deleteTransaction(onSuccess: () -> Unit) {
    val capturedId = transactionId
    val capturedArbitrary = arbitraryFlag

    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        capturedId?.let {
          runCatching { deleteTransactionUseCase.execute(capturedId, capturedArbitrary) }
            .onSuccess {
              withContext(Dispatchers.Main) {
                onSuccess()
              }
            }
            .onFailure { error ->
              val s = _state.value
              if (s is TransactionEditCreateState.Target) {
                _state.update {
                  s.copy(
                    result = s.result.copy(),
                    transactionSyncError = error,
                    sameErrorCounter = if (error == s.transactionSyncError) s.sameErrorCounter + 1 else s.sameErrorCounter
                  )
                }
              }
            }
        }
      }
    }
  }

  private fun updateAmount(new : String) {
    val s = _state.value
    if (s is TransactionEditCreateState.Target) {
      _state.update {
        s.copy(result = s.result.copy(amount = new.formatCorrectAndSign()))
      }
    }
  }

  private fun updateCategory(id : Int, name : String) {
    val s = _state.value
    if (s is TransactionEditCreateState.Target) {
      _state.update {
        s.copy(
          result = s.result.copy(
            categoryId = id,
            categoryName = name
          )
        )
      }
    }
  }

  private fun updateDate(new : Long?) {
    val s = _state.value
    if (s is TransactionEditCreateState.Target) {
      _state.update { s.copy(result = s.result.copy(date = new?.toDateStringDDMMYYYY() ?: "")) }
    }
  }

  private fun updateTime(new : PickedTime) {
    val s = _state.value
    if (s is TransactionEditCreateState.Target) {
      _state.update { s.copy(result = s.result.copy(time = new.formatToTimeString())) }
    }
  }

  private fun updateComment(new : String) {
    val s = _state.value
    if (s is TransactionEditCreateState.Target) {
      _state.update { s.copy(result = s.result.copy(comment = new)) }
    }
  }

  private fun updateTransaction(model: TransactionInfoModel, onSuccess : () -> Unit) {
    val capturedId = transactionId

    viewModelScope.launch {
      withContext(Dispatchers.IO){
        runCatching {
          if (capturedId == null) {
            createTransactionUseCase.execute(model)
          } else {
            updateTransactionUseCase.execute(capturedId, model)
          }
        }
          .onSuccess {
            withContext(Dispatchers.Main) {
              onSuccess()
            }
          }
          .onFailure { error ->
            val s = _state.value
            if (s is TransactionEditCreateState.Target){
              _state.update {
                s.copy(
                  result = s.result.copy(),
                  transactionSyncError = error,
                  sameErrorCounter = if (error == s.transactionSyncError) s.sameErrorCounter + 1 else s.sameErrorCounter
                )
              }
            }
          }
      }
    }
  }

  private fun initializeStartingState(id : Int?, isIncome: Boolean, isArbitrary : Boolean? = null) {
    transactionId = id
    this.arbitraryFlag = isArbitrary

    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { TransactionEditCreateState.Loading }

        runCatching { loadCategoriesUseCase.execute() }
          .onSuccess { result ->
            result.collectLatest { categoryModels ->
              runCatching { loadTransactionUseCase.execute(id, isArbitrary) }
                .onSuccess { transactionInfoModel ->
                  _state.update {
                    TransactionEditCreateState.Target(
                      result = transactionInfoModel,
                      transactionSyncError = null,
                      availableCategories = categoryModels.filter { it.isIncome == isIncome }
                    )
                  }
                }
                .onFailure { error ->
                  _state.update {
                    TransactionEditCreateState.Error(
                      YMSError(
                        R.string.transaction_error_desc,
                        error
                      )
                    )
                  }
                }
            }
          }
          .onFailure { error ->
            _state.update {
              TransactionEditCreateState.Error(
                YMSError(
                  R.string.category_error_desc,
                  error
                )
              )
            }
            return@withContext
          }
      }
    }
  }
}