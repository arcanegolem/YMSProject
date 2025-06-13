package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.domain.repos.CategoriesRepository

class LoadCategoriesUseCase(
  private val categoriesRepository: CategoriesRepository
) {
  suspend fun execute() : List<CategoryModel> {
    return categoriesRepository.loadCategories()
  }
}