package arcanegolem.yms.project.categories

sealed class CategoriesEvent {
  data object LoadCategories : CategoriesEvent()
}