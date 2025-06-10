package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.CategoryModel

interface CategoriesRepository {
  suspend fun loadCategories() : List<CategoryModel>
}