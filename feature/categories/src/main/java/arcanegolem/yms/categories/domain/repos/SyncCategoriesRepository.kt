package arcanegolem.yms.categories.domain.repos

interface SyncCategoriesRepository {
  suspend fun syncCategories()
}