package arcanegolem.yms.categories.domain.repos

import arcanegolem.yms.categories.domain.models.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
  /**
   * Загружает список статей
   *
   * @return [List] из [arcanegolem.yms.categories.domain.models.CategoryModel]
   */
  suspend fun loadCategories() : Flow<List<CategoryModel>>
}