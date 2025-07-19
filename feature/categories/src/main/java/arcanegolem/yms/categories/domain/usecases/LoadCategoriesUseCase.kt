package arcanegolem.yms.categories.domain.usecases

import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Юзкейс для получения списка доступных статей см. [CategoryModel]
 *
 * @param categoriesRepository имплементация [CategoriesRepository]
 */
class LoadCategoriesUseCase @Inject constructor(
  private val categoriesRepository: CategoriesRepository
) {
  suspend fun execute() : Flow<List<CategoryModel>> {
    return categoriesRepository.loadCategories()
  }
}
