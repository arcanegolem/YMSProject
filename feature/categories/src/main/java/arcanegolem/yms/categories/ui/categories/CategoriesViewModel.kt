package arcanegolem.yms.categories.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.categories.domain.usecases.LoadCategoriesUseCase
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
  private val loadCategoriesUseCase: LoadCategoriesUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<CategoriesState>(CategoriesState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : CategoriesEvent) {
    when (event) {
      CategoriesEvent.LoadCategories -> loadCategories()
    }
  }

  private var categoriesLoadingJob : Job? = null
  
  private fun loadCategories() {
    categoriesLoadingJob?.cancel()
    categoriesLoadingJob = null
    
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { CategoriesState.Loading }
        runCatching { loadCategoriesUseCase.execute() }
          .onSuccess { result ->
            categoriesLoadingJob = launch {
              result.collectLatest { categoryModels ->
                _state.update { CategoriesState.Target(categoryModels) }
              }
            }
          }
          .onFailure { error ->
            _state.update {
              CategoriesState.Error(
                YMSError(
                  R.string.category_error_desc,
                  error
                )
              )
            }
          }
      }
    }
  }
}
