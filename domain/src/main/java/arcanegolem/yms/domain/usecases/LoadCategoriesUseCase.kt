package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.domain.repos.CategoriesRepository

/**
 * Юзкейс для получения списка доступных статей см. [CategoryModel]
 *
 * @param categoriesRepository имплементация [CategoriesRepository]
 */
class LoadCategoriesUseCase(
  private val categoriesRepository: CategoriesRepository
) {
  suspend fun execute() : List<CategoryModel> {
    return categoriesRepository.loadCategories()
  }
}
