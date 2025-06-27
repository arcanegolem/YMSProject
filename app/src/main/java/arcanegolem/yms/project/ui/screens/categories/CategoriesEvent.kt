package arcanegolem.yms.project.ui.screens.categories

sealed class CategoriesEvent {
  data object LoadCategories : CategoriesEvent()
}
