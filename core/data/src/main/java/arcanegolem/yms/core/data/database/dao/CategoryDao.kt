package arcanegolem.yms.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import arcanegolem.yms.core.data.database.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories WHERE categoryId = :id")
  suspend fun getCategoryById(id : Int) : CategoryEntity?

  @Query("SELECT * FROM categories")
  fun getAllCategories() : Flow<List<CategoryEntity>>

  @Upsert suspend fun upsertCategory(category: CategoryEntity)
  @Delete suspend fun deleteCategory(category: CategoryEntity)

  @Query("DELETE FROM categories WHERE categoryId = :id")
  suspend fun deleteById(id : Int)
}