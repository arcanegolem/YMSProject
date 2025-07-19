package arcanegolem.yms.categories.data.repos

import arcanegolem.yms.categories.data.remote.api.Categories
import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.entities.CategoryEntity
import arcanegolem.yms.core.data.remote.models.Category
import arcanegolem.yms.core.utils.NetworkMonitor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Реальная имплементация репозитория статей
 *
 * @param httpClient Ktor http-клиент
 * @param categoryDao
 */
class CategoriesRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient,
  private val categoryDao: CategoryDao
) : CategoriesRepository {
  override suspend fun loadCategories(): Flow<List<CategoryModel>> {
    if (NetworkMonitor.networkAvailable.value) {
      val response = httpClient.get(Categories()).body<List<Category>>()

      response.forEach { remote ->
        categoryDao.upsertCategory(
          CategoryEntity(
            categoryId = remote.id,
            name = remote.name,
            emoji = remote.emoji,
            isIncome = remote.isIncome
          )
        )
      }
    }

    return categoryDao.getAllCategories().map { entityList ->
      entityList.map {
        CategoryModel(
          id = it.categoryId,
          name = it.name,
          emoji = it.emoji,
          isIncome = it.isIncome
        )
      }
    }
  }
}
