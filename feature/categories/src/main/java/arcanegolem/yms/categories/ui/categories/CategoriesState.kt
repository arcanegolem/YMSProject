package arcanegolem.yms.categories.ui.categories

import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.core.ui.components.state_handlers.error.YMSError

sealed class CategoriesState {
  data object Idle : CategoriesState()
  data object Loading : CategoriesState()
  data class Target(val result : List<CategoryModel>) : CategoriesState()
  data class Error(val error : YMSError) : CategoriesState()
}
