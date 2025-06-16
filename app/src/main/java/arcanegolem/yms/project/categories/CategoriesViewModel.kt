package arcanegolem.yms.project.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arcanegolem.yms.domain.usecases.LoadCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel(
  private val loadCategoriesUseCase: LoadCategoriesUseCase
) : ViewModel() {
  private val _state = MutableStateFlow<CategoriesState>(CategoriesState.Idle)
  val state get() = _state.asStateFlow()

  fun processEvent(event : CategoriesEvent) {
    when (event) {
      CategoriesEvent.LoadCategories -> loadCategories()
    }
  }

  private fun loadCategories() {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        _state.update { CategoriesState.Loading }
        val result = loadCategoriesUseCase.execute()
        _state.update { CategoriesState.Target(result) }
      }
    }
  }
}