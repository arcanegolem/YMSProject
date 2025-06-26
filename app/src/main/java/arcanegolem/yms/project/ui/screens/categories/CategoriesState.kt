package arcanegolem.yms.project.ui.screens.categories

import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.project.ui.components.state_handlers.error.YMSError

sealed class CategoriesState {
  data object Idle : CategoriesState()
  data object Loading : CategoriesState()
  data class Target(val result : List<CategoryModel>) : CategoriesState()
  data class Error(val error : YMSError) : CategoriesState()
}