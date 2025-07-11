package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.domain.repos.CategoriesRepository
import javax.inject.Inject

/**
 * Юзкейс для получения списка доступных статей см. [CategoryModel]
 *
 * @param categoriesRepository имплементация [CategoriesRepository]
 */
class LoadCategoriesUseCase @Inject constructor(
  private val categoriesRepository: CategoriesRepository
) {
  suspend fun execute() : List<CategoryModel> {
    return categoriesRepository.loadCategories()
  }
}
