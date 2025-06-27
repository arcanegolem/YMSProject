package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.CategoryModel

interface CategoriesRepository {
  /**
   * Загружает список статей
   *
   * @return [List] из [CategoryModel]
   */
  suspend fun loadCategories() : List<CategoryModel>
}
