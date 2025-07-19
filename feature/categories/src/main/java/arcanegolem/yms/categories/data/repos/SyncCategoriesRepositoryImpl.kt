package arcanegolem.yms.categories.data.repos

import arcanegolem.yms.categories.data.remote.api.Categories
import arcanegolem.yms.categories.domain.repos.SyncCategoriesRepository
import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.entities.CategoryEntity
import arcanegolem.yms.core.data.remote.models.Category
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class SyncCategoriesRepositoryImpl(
  private val httpClient: HttpClient,
  private val categoryDao: CategoryDao
) : SyncCategoriesRepository {
  override suspend fun syncCategories() {
    val response = httpClient.get(Categories())

    if (response.status.value == 200) {
      val body = response.body<List<Category>>()

      body.forEach { remote ->
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
  }
}