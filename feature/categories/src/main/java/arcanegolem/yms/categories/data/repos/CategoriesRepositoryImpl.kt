package arcanegolem.yms.categories.data.repos

import arcanegolem.yms.categories.data.remote.api.Categories
import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.core.data.remote.models.Category
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

/**
 * Реальная имплементация репозитория статей
 *
 * @param httpClient Ktor http-клиент
 */
class CategoriesRepositoryImpl @Inject constructor(
  private val httpClient: HttpClient
) : CategoriesRepository {
  override suspend fun loadCategories(): List<CategoryModel> {
    val response = httpClient.get(Categories()).body<List<Category>>()

    return response.map { categoryRemote ->
      CategoryModel(
        id = categoryRemote.id,
        name = categoryRemote.name,
        emoji = categoryRemote.emoji
      )
    }
  }
}
