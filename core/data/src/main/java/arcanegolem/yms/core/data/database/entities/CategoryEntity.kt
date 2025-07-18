package arcanegolem.yms.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class CategoryEntity(
  @PrimaryKey(autoGenerate = false)
  val categoryId : Int,
  val name : String,
  val emoji : String,
  val isIncome : Boolean
)
