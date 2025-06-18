package arcanegolem.yms.data.repos

import arcanegolem.yms.data.remote.models.Category
import arcanegolem.yms.data.remote.api.Categories
import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.domain.repos.CategoriesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class CategoriesRepositoryRemoteImpl(
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