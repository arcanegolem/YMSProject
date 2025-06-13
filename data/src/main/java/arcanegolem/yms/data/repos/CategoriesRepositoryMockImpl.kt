package arcanegolem.yms.data.repos

import arcanegolem.yms.data.mock.mockCategories
import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.domain.repos.CategoriesRepository

class CategoriesRepositoryMockImpl : CategoriesRepository {
  override suspend fun loadCategories(): List<CategoryModel> {
    val loadedData = mockCategories
    return loadedData.map { categoryRemote ->
      CategoryModel(
        id = categoryRemote.id,
        name = categoryRemote.name,
        emoji = categoryRemote.emoji
      )
    }
  }
}