package arcanegolem.yms.categories.ui.categories

sealed class CategoriesEvent {
  data object LoadCategories : CategoriesEvent()
}
